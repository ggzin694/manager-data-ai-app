package com.managerdataai.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {
  public void onCreate(Bundle b){ super.onCreate(b); LinearLayout root=new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setPadding(28,36,28,20); root.setBackgroundColor(Color.rgb(16,17,26));
    TextView title=new TextView(this); title.setText("Manager Data AI"); title.setTextColor(Color.WHITE); title.setTextSize(28); title.setGravity(Gravity.CENTER); root.addView(title,new LinearLayout.LayoutParams(-1,70));
    TextView status=new TextView(this); status.setText("Sua central de inteligência começa aqui.\nModo local ativo"); status.setTextColor(Color.LTGRAY); status.setTextSize(16); status.setGravity(Gravity.CENTER); root.addView(status,new LinearLayout.LayoutParams(-1,100));
    EditText input=new EditText(this); input.setHint("Digite uma mensagem..."); input.setTextColor(Color.WHITE); input.setHintTextColor(Color.GRAY); root.addView(input,new LinearLayout.LayoutParams(-1,0,1));
    Button send=new Button(this); send.setText("Enviar"); send.setOnClickListener(v->{ if(input.getText().length()>0){ status.setText("Mensagem recebida localmente.\nA conexão com o backend será adicionada depois."); input.setText(""); }}); root.addView(send,new LinearLayout.LayoutParams(-1,60)); setContentView(root); }
}
