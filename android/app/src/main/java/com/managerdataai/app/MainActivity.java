package com.managerdataai.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.*;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class MainActivity extends Activity {
  private static final String API_URL = "https://manager-data-ai-backend.onrender.com/api/chat";
  private int dp(float v) { return (int)(v * getResources().getDisplayMetrics().density + 0.5f); }
  @Override public void onCreate(Bundle b) {
    super.onCreate(b);
    LinearLayout root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(dp(24),dp(28),dp(24),dp(24)); root.setBackgroundColor(Color.rgb(16,17,26));
    TextView title = new TextView(this); title.setText("Manager Data AI"); title.setTextColor(Color.WHITE); title.setTextSize(28); title.setTypeface(Typeface.DEFAULT,Typeface.BOLD); title.setGravity(Gravity.CENTER); root.addView(title,new LinearLayout.LayoutParams(-1,-2));
    TextView status = new TextView(this); status.setText("Sua central de inteligência começa aqui.\nBackend online"); status.setTextColor(Color.LTGRAY); status.setTextSize(16); status.setGravity(Gravity.CENTER); LinearLayout.LayoutParams sp=new LinearLayout.LayoutParams(-1,-2); sp.setMargins(0,dp(12),0,dp(18)); root.addView(status,sp);
    EditText input = new EditText(this); input.setHint("Digite uma mensagem..."); input.setTextColor(Color.WHITE); input.setHintTextColor(Color.GRAY); input.setTextSize(16); input.setGravity(Gravity.TOP|Gravity.START); input.setPadding(dp(16),dp(14),dp(16),dp(14)); input.setMinHeight(dp(90)); LinearLayout.LayoutParams ip=new LinearLayout.LayoutParams(-1,0,1f); ip.setMargins(0,0,0,dp(16)); root.addView(input,ip);
    Button send = new Button(this); send.setText("Enviar"); send.setTextSize(16); send.setMinHeight(dp(56)); send.setAllCaps(false); send.setOnClickListener(v -> { String msg=input.getText().toString().trim(); if(!msg.isEmpty()){ send.setEnabled(false); status.setText("Conectando ao backend..."); Executors.newSingleThreadExecutor().execute(() -> { String reply=callApi(msg); runOnUiThread(() -> { status.setText(reply); send.setEnabled(true); }); }); } }); root.addView(send,new LinearLayout.LayoutParams(-1,-2)); setContentView(root);
  }
  private String callApi(String msg) { try { URL u=new URL(API_URL); HttpURLConnection c=(HttpURLConnection)u.openConnection(); c.setRequestMethod("POST"); c.setConnectTimeout(15000); c.setReadTimeout(20000); c.setDoOutput(true); c.setRequestProperty("Content-Type","application/json"); String body="{\"message\":\""+json(msg)+"\",\"agent\":\"central\"}"; try(OutputStream o=c.getOutputStream()){o.write(body.getBytes("UTF-8"));} BufferedReader r=new BufferedReader(new InputStreamReader(c.getInputStream())); StringBuilder s=new StringBuilder(); String line; while((line=r.readLine())!=null)s.append(line); String x=s.toString(); int p=x.indexOf("\"reply\":\""); if(p>=0){p+=10; int e=x.indexOf("\"",p); return x.substring(p,e).replace("\\n","\n");} return x; } catch(Exception e){ return "Não foi possível conectar agora. O modo local será restaurado na próxima versão."; } }
  private String json(String s){ return s.replace("\\","\\\\").replace("\"","\\\"").replace("\n","\\n"); }
}
