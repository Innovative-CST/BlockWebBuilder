package com.dragon.ide.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
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
          final LinearLayout ll_source = new LinearLayout(view.getContext());
          ll_source.setPadding(25, 0, 25, 0);
          ll_source.setBackgroundColor(Color.WHITE);
          final TextView tvTextContent = new TextView(view.getContext());
          tvTextContent.setText(((SourceContent) blockContent.get(i)).getValue());
          ll_source.addView(tvTextContent, view.getChildCount() - 1);

          final LinearLayout.LayoutParams layoutParams =
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
                new BlockContentLoader()
                .new SourceContentClickListener(tvTextContent, sc, activity, language));
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

  public class SourceContentClickListener implements View.OnClickListener {
    public TextView tvTextContent;
    public SourceContent blockContent;
    public Activity activity;
    public String language;

    public SourceContentClickListener(
        TextView param1, SourceContent param2, Activity param3, String param4) {
      tvTextContent = param1;
      blockContent = param2;
      activity = param3;
      language = param4;
    }

    @Override
    public void onClick(View view) {
      ValueEditorDialog valueEditorDialog =
          new ValueEditorDialog(
              activity,
              blockContent.getValue(),
              language,
              new ValueListener() {

                @Override
                public void onSubmitted(String value) {
                  blockContent.setValue(value);
                  tvTextContent.setText(blockContent.getValue());
                }

                @Override
                public void onError(String error) {}
              });
      valueEditorDialog.show();
    }
  }
}
