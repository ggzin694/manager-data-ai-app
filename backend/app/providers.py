"""Provider adapters. External providers are opt-in and never guessed as connected."""
import json
import os
from dataclasses import dataclass
from urllib.request import Request, urlopen

@dataclass(frozen=True)
class ProviderResult:
    reply: str
    provider: str
    model: str
    configured: bool
    fallback: bool = False

class LocalDeterministicProvider:
    name = "local"
    model = "local-deterministic"
    def complete(self, message: str, agent_name: str, instruction: str) -> ProviderResult:
        return ProviderResult(reply=f"[{agent_name}] Recebi sua mensagem: {message}", provider=self.name, model=self.model, configured=True)

class OpenAICompatibleProvider:
    name = "openai-compatible"
    def __init__(self, api_key: str, base_url: str, model: str):
        self.api_key, self.base_url, self.model = api_key, base_url.rstrip("/"), model
    def complete(self, message: str, agent_name: str, instruction: str) -> ProviderResult:
        payload = {"model": self.model, "messages": [{"role": "system", "content": instruction}, {"role": "user", "content": message}]}
        request = Request(self.base_url + "/chat/completions", data=json.dumps(payload).encode(), method="POST", headers={"Authorization": "Bearer " + self.api_key, "Content-Type": "application/json"})
        with urlopen(request, timeout=20) as response:
            data = json.loads(response.read().decode())
        return ProviderResult(reply=data["choices"][0]["message"]["content"], provider=self.name, model=self.model, configured=True)

def configured_provider():
    provider = os.getenv("MANAGER_AI_PROVIDER", "local").strip().lower()
    key = os.getenv("OPENAI_API_KEY", "").strip()
    if provider in {"openai", "openai-compatible"} and key:
        return OpenAICompatibleProvider(key, os.getenv("OPENAI_BASE_URL", "https://api.openai.com/v1"), os.getenv("OPENAI_MODEL", "gpt-4o-mini"))
    return LocalDeterministicProvider()
