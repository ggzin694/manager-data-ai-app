# Manager Data AI backend

Minimal FastAPI service for the Android app's future `/api/chat` connection. Local and deterministic: no provider calls, API keys, or secrets.

## Run

```bash
cd backend
python -m venv .venv
. .venv/bin/activate
pip install -r requirements.txt
uvicorn app.main:app --reload --host 127.0.0.1 --port 8000
```

## API

`GET /health` returns status. `POST /api/chat` accepts `{ "message": "..." }` and returns a deterministic reply. Docs: `/docs`.

CORS is restricted to localhost development origins; configure explicit HTTPS origins before production.

