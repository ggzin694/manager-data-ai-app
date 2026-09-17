from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel,Field
app=FastAPI(title='Manager Data AI Backend')
app.add_middleware(CORSMiddleware,allow_origins=['http://localhost','http://127.0.0.1'],allow_methods=['GET','POST'],allow_headers=['Content-Type'])
class Chat(BaseModel):
 message:str=Field(...,min_length=1,max_length=4000)
@app.get('/health')
def health(): return {'status':'ok','service':'manager-data-ai-backend'}
@app.post('/api/chat')
def chat(body:Chat): return {'reply':f'Recebi sua mensagem: {body.message}','mode':'local-deterministic'}
