from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel, Field
from .registry import get_agent
from .providers import configured_provider

app = FastAPI(title="Manager Data AI Backend")
app.add_middleware(CORSMiddleware, allow_origins=["http://localhost", "http://127.0.0.1"], allow_methods=["GET", "POST"], allow_headers=["Content-Type"])

class Chat(BaseModel):
    message: str = Field(..., min_length=1, max_length=4000)
    agent: str | None = Field(default=None, max_length=50)
    model: str | None = Field(default=None, max_length=120)

@app.get("/health")
def health():
    return {"status": "ok", "service": "manager-data-ai-backend"}

@app.post("/api/chat")
def chat(body: Chat):
    spec = get_agent(body.agent)
    provider = configured_provider()
    if body.model and provider.name == "openai-compatible":
        provider = type(provider)(provider.api_key, provider.base_url, body.model.strip()[:120] or provider.model)
    result = provider.complete(body.message, spec.name, spec.instruction)
    return {"reply": result.reply, "selected_agent": spec.name, "selected_model": result.model, "provider": result.provider, "provider_configured": result.configured, "fallback": result.fallback}
