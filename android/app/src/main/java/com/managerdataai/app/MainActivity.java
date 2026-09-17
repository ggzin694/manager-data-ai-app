package com.managerdataai.app;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.*;

public class MainActivity extends Activity {
  private int dp(float value) {
    return (int) (value * getResources().getDisplayMetrics().density + 0.5f);
  }

  @Override
  public void onCreate(Bundle b) {
    super.onCreate(b);

    LinearLayout root = new LinearLayout(this);
    root.setOrientation(LinearLayout.VERTICAL);
    root.setPadding(dp(24), dp(28), dp(24), dp(24));
    root.setBackgroundColor(Color.rgb(16, 17, 26));

    TextView title = new TextView(this);
    title.setText("Manager Data AI");
    title.setTextColor(Color.WHITE);
    title.setTextSize(28);
    title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
    title.setGravity(Gravity.CENTER);
    root.addView(title, new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    TextView status = new TextView(this);
    status.setText("Sua central de inteligência começa aqui.\nModo local ativo");
    status.setTextColor(Color.LTGRAY);
    status.setTextSize(16);
    status.setGravity(Gravity.CENTER);
    LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    statusParams.setMargins(0, dp(12), 0, dp(18));
    root.addView(status, statusParams);

    EditText input = new EditText(this);
    input.setHint("Digite uma mensagem...");
    input.setTextColor(Color.WHITE);
    input.setHintTextColor(Color.GRAY);
    input.setTextSize(16);
    input.setGravity(Gravity.TOP | Gravity.START);
    input.setPadding(dp(16), dp(14), dp(16), dp(14));
    input.setMinHeight(dp(90));
    LinearLayout.LayoutParams inputParams = new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f);
    inputParams.setMargins(0, 0, 0, dp(16));
    root.addView(input, inputParams);

    Button send = new Button(this);
    send.setText("Enviar");
    send.setTextSize(16);
    send.setMinHeight(dp(56));
    send.setAllCaps(false);
    send.setOnClickListener(v -> {
      if (input.getText().toString().trim().length() > 0) {
        status.setText("Mensagem recebida localmente.\nA conexão com o backend será adicionada depois.");
        input.setText("");
      }
    });
    root.addView(send, new LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

    setContentView(root);
  }
}
