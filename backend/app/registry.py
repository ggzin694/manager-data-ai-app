"""Agent and model metadata. Keep this module free of provider calls and secrets."""
from dataclasses import dataclass

@dataclass(frozen=True)
class AgentSpec:
    name: str
    description: str
    instruction: str

AGENTS = {
    "central": AgentSpec("central", "Coordenação geral", "Organize the request and answer clearly."),
    "creative": AgentSpec("creative", "Ideias e criação", "Favor originality, alternatives and concrete creative direction."),
    "study": AgentSpec("study", "Estudos e aprendizagem", "Explain progressively, with examples and a short next step."),
    "programming": AgentSpec("programming", "Programação", "Prioritize correctness, safety, maintainability and useful code."),
    "research": AgentSpec("research", "Pesquisa", "Separate known facts, uncertainty and what should be verified."),
    "data": AgentSpec("data", "Dados", "Structure information, identify assumptions and suggest useful analysis."),
    "security": AgentSpec("security", "Segurança defensiva", "Focus on defensive, authorized and privacy-preserving guidance."),
}

def get_agent(name: str | None) -> AgentSpec:
    return AGENTS.get((name or "central").strip().lower(), AGENTS["central"])

