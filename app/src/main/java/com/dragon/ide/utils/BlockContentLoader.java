package com.dragon.ide.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dragon.ide.listeners.ValueListener;
import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.ComplexBlockContent;
import com.dragon.ide.objects.blockcontent.SourceContent;
import com.dragon.ide.ui.dialogs.eventeditor.ValueEditorDialog;
import java.util.ArrayList;

public class BlockContentLoader {
  public static void loadBlockContent(
      ArrayList<Object> blockContent,
      ViewGroup view,
      String color,
      String language,
      Activity activity,
      boolean enableEdit) {
    for (int i = 0; i < blockContent.size(); ++i) {
      if (blockContent.get(i) instanceof ComplexBlockContent) {
        if (blockContent.get(i) instanceof SourceContent) {
          LinearLayout ll_source = new LinearLayout(view.getContext());
          ll_source.setPadding(25, 0, 25, 0);
          ll_source.setBackgroundColor(Color.WHITE);
          TextView tvTextContent = new TextView(view.getContext());
          tvTextContent.setText(((SourceContent) blockContent.get(i)).getValue());
          ll_source.addView(tvTextContent, view.getChildCount() - 1);

          LinearLayout.LayoutParams layoutParams =
              new LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          int startMargin = 8;
          int endMargin = 8;
          layoutParams.setMarginStart(startMargin);
          layoutParams.setMarginEnd(endMargin);
          ll_source.setLayoutParams(layoutParams);

          view.addView(ll_source, view.getChildCount());

          final SourceContent sc = (SourceContent) blockContent.get(i);
          if (enableEdit) {
            ll_source.setOnClickListener(
                (v) -> {
                  ValueEditorDialog valueEditorDialog =
                      new ValueEditorDialog(
                          activity,
                          sc.getValue(),
                          language,
                          new ValueListener() {

                            @Override
                            public void onSubmitted(String value) {
                              sc.setValue(value);
                              tvTextContent.setText(sc.getValue());
                            }

                            @Override
                            public void onError(String error) {}
                          });
                  valueEditorDialog.show();
                });
          }
        }
      } else if (blockContent.get(i) instanceof BlockContent) {
        TextView tvTextContent = new TextView(view.getContext());
        tvTextContent.setText(((BlockContent) blockContent.get(i)).getText());

        int backgroundColor = Color.parseColor(color);
        int red = Color.red(backgroundColor);
        int green = Color.green(backgroundColor);
        int blue = Color.blue(backgroundColor);

        double luminance = (0.2126 * red + 0.7152 * green + 0.0722 * blue) / 255;
        double contrastRatioThreshold = 1.4;
        int textColor = (luminance > 0.5 * 255) ? Color.BLACK : Color.WHITE;
        double contrastRatio = (luminance + 0.05) / (0.05 + 0.05);

        if (contrastRatio < contrastRatioThreshold) {
          tvTextContent.setTextColor(textColor);
        }

        view.addView(tvTextContent, view.getChildCount() - 1);
      }
    }
  }
}
