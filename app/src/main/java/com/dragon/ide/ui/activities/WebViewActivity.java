/*
 *  This file is part of Android Code Editor.
 *
 *  Android Code Editor is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Android Code Editor is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with Android Code Editor.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.dragon.ide.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;
import com.dragon.ide.R;
import com.dragon.ide.utils.SimpleHttpServer;
import com.dragon.ide.utils.Utils;

public class WebViewActivity extends BaseActivity {

  private WebView webview;
  private LinearLayout consoleView;
  private ScrollView console_content;
  private EditText executeCodeInWebView;
  public String initialUrl = "";
  public SimpleHttpServer hoster;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_web_view);

    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle("WebView");
    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeButtonEnabled(true);
    toolbar.setNavigationOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View arg0) {
            onBackPressed();
          }
        });

    webview = findViewById(R.id.webview);
    consoleView = findViewById(R.id.console);
    console_content = findViewById(R.id.console_content);
    executeCodeInWebView = findViewById(R.id.execute);
    webview.getSettings().setJavaScriptEnabled(true);
    webview.getSettings().setSupportZoom(true);
    webview.getSettings().setAllowContentAccess(true);
    webview.getSettings().setAllowFileAccess(true);
    webview.getSettings().setBuiltInZoomControls(true);
    webview.getSettings().setDisplayZoomControls(false);
    webview.setWebChromeClient(
        new WebChromeClient() {
          @Override
          public boolean onConsoleMessage(ConsoleMessage console) {
            if (consoleView != null) {
              ViewGroup view =
                  ((ViewGroup) getLayoutInflater().inflate(R.layout.layout_console_log_item, null));
              TextView consoleTextView = view.findViewById(R.id.console);
              consoleTextView.setText(console.message());
              TextView consoleDetail = view.findViewById(R.id.console_detail);
              consoleDetail.setText(
                  console.sourceId().concat(":").concat(String.valueOf(console.lineNumber())));
              consoleView.addView(view, 0);
              consoleDetail.setTextColor(Color.GRAY);
              if (console.messageLevel().equals(ConsoleMessage.MessageLevel.DEBUG)) {
                consoleTextView.setTextColor(Color.CYAN);
              } else if (console.messageLevel().equals(ConsoleMessage.MessageLevel.ERROR)) {
                consoleTextView.setTextColor(Color.RED);
              } else if (console.messageLevel().equals(ConsoleMessage.MessageLevel.TIP)) {
                consoleTextView.setTextColor(Color.CYAN);
              } else if (console.messageLevel().equals(ConsoleMessage.MessageLevel.WARNING)) {
                consoleTextView.setTextColor(Color.parseColor("#F28500"));
              }
            }
            return super.onConsoleMessage(console);
          }
        });

    webview.setWebViewClient(new WebViewClient());

    if (getIntent().getStringExtra("type") != null
        && getIntent().getStringExtra("type").equals("file")) {
      webview.loadUrl("file:".concat(getIntent().getStringExtra("data")));
      hoster =
          new SimpleHttpServer(
              8080,
              getIntent().getStringExtra("root"),
              getIntent()
                  .getStringExtra("data")
                  .substring(getIntent().getStringExtra("root").length()));
      hoster.startServer();
      initialUrl = hoster.getLocalIpAddress();
      webview.loadUrl(initialUrl);
    }

    findViewById(R.id.console_slider).setVisibility(View.VISIBLE);
    console_content.setVisibility(View.VISIBLE);

    executeCodeInWebView.setOnEditorActionListener(
        new TextView.OnEditorActionListener() {
          @Override
          public boolean onEditorAction(TextView edittext, int action, KeyEvent event) {
            if (action == EditorInfo.IME_ACTION_DONE
                || event.getAction() == KeyEvent.ACTION_DOWN
                    && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
              webview.loadUrl("javascript:".concat(edittext.getText().toString()));
              edittext.setText("");
              return true;
            }
            return false;
          }
        });

    findViewById(R.id.console_slider)
        .setOnTouchListener(
            new View.OnTouchListener() {
              int initialHeight;
              float initialY;

              @Override
              public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                  case MotionEvent.ACTION_DOWN:
                    initialHeight = console_content.getHeight();
                    initialY = motionEvent.getRawY();
                    return true;
                  case MotionEvent.ACTION_MOVE:
                    float currentY = motionEvent.getRawY();
                    float dy = currentY - initialY;
                    if ((initialHeight + (int) dy) >= 0) {
                      if ((initialHeight + (int) dy + Utils.dpToPx(WebViewActivity.this, 5))
                          <= findViewById(R.id.mainContainer).getHeight()) {
                        ViewGroup.LayoutParams layoutParams = console_content.getLayoutParams();
                        layoutParams.height = initialHeight + (int) dy;
                        console_content.setLayoutParams(layoutParams);
                      }
                    }
                    return true;
                }
                return false;
              }
            });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (hoster != null) {
      hoster.stopServer();
    }
  }

  @Override
  public void onBackPressed() {
    if (hoster != null) {
      hoster.stopServer();
    }
    finish();
  }
}
