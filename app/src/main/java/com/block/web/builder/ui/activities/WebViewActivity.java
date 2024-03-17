/*
 * This file is part of BlockWeb Builder [https://github.com/TS-Code-Editor/BlockWebBuilder].
 *
 * License Agreement
 * This software is licensed under the terms and conditions outlined below. By accessing, copying, modifying, or using this software in any way, you agree to abide by these terms.
 *
 * 1. **  Copy and Modification Restrictions  **
 *    - You are not permitted to copy or modify the source code of this software without the permission of the owner, which may be granted publicly on GitHub Discussions or on Discord.
 *    - If permission is granted by the owner, you may copy the software under the terms specified in this license agreement.
 *    - You are not allowed to permit others to copy the source code that you were allowed to copy by the owner.
 *    - Modified or copied code must not be further copied.
 * 2. **  Contributor Attribution  **
 *    - You must attribute the contributors by creating a visible list within the application, showing who originally wrote the source code.
 *    - If you copy or modify this software under owner permission, you must provide links to the profiles of all contributors who contributed to this software.
 * 3. **  Modification Documentation  **
 *    - All modifications made to the software must be documented and listed.
 *    - the owner may incorporate the modifications made by you to enhance this software.
 * 4. **  Consistent Licensing  **
 *    - All copied or modified files must contain the same license text at the top of the files.
 * 5. **  Permission Reversal  **
 *    - If you are granted permission by the owner to copy this software, it can be revoked by the owner at any time. You will be notified at least one week in advance of any such reversal.
 *    - In case of Permission Reversal, if you fail to acknowledge the notification sent by us, it will not be our responsibility.
 * 6. **  License Updates  **
 *    - The license may be updated at any time. Users are required to accept and comply with any changes to the license.
 *    - In such circumstances, you will be given 7 days to ensure that your software complies with the updated license.
 *    - We will not notify you about license changes; you need to monitor the GitHub repository yourself (You can enable notifications or watch the repository to stay informed about such changes).
 * By using this software, you acknowledge and agree to the terms and conditions outlined in this license agreement. If you do not agree with these terms, you are not permitted to use, copy, modify, or distribute this software.
 *
 * Copyright © 2024 Dev Kumar
 */

package com.block.web.builder.ui.activities;

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
import com.block.web.builder.R;
import com.block.web.builder.utils.SimpleHttpServer;
import com.block.web.builder.utils.Utils;

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
              8080, getIntent().getStringExtra("root"), getIntent().getStringExtra("data"));
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
