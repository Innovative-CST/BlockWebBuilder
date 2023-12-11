package com.dragon.ide.utils;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dragon.ide.R;
import com.dragon.ide.objects.Block;
import com.dragon.ide.objects.BlockContent;
import com.dragon.ide.objects.ComplexBlockContent;
import com.dragon.ide.objects.blockcontent.BooleanContent;
import com.dragon.ide.objects.blockcontent.SourceContent;
import com.dragon.ide.ui.activities.EventEditorActivity;
import com.dragon.ide.ui.view.BlockDefaultView;
import editor.tsd.editors.ace.AceEditorColors;
import editor.tsd.tools.EditorListeners;
import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;
import java.util.ArrayList;

public class BlockContentLoader {
  public static void loadBlockContent(
      ArrayList<BlockContent> blockContent,
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
          ll_source.setBackgroundColor(
              ColorUtils.getColor(activity, com.google.android.material.R.attr.colorSurface));
          final TextView tvTextContent = new TextView(view.getContext());
          tvTextContent.setSingleLine(true);
          tvTextContent.setText(
              Utils.setWordLimitOnString(50, ((SourceContent) blockContent.get(i)).getValue()));
          updateContentPaddingWithText(tvTextContent, ll_source);
          tvTextContent.setTextSize(12);
          tvTextContent.setTextColor(
              ColorUtils.getColor(activity, com.google.android.material.R.attr.colorOnSurface));
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
                .new SourceContentClickListener(tvTextContent, sc, activity, language, ll_source));
          }
        } else if (blockContent.get(i) instanceof BooleanContent) {
          final ComplexBlockContent cbc = ((ComplexBlockContent) blockContent.get(i));
          final LinearLayout ll_boolean =
              new LinearLayout(view.getContext()) {
                @Override
                public void addView(View v) {
                  super.addView(v);
                  setAlpha(1f);
                  if (v instanceof BlockDefaultView) {
                    cbc.setBlock(((BlockDefaultView) v).getBlock());
                  }
                }

                @Override
                public void addView(View v, int index) {
                  super.addView(v, index);
                  setAlpha(1f);
                  if (v instanceof BlockDefaultView) {
                    cbc.setBlock(((BlockDefaultView) v).getBlock());
                  }
                }

                @Override
                public void removeView(View v) {
                  super.removeView(v);
                  if (getChildCount() == 0) {
                    setAlpha(0.3f);
                  } else {
                    setAlpha(1f);
                  }
                  if (v instanceof BlockDefaultView) {
                    cbc.setValue("");
                  }
                }
              };
          ll_boolean.setTag(((ComplexBlockContent) blockContent.get(i)).getAcceptance());
          ll_boolean.setBackgroundResource(R.drawable.block_boolean_bg);
          Drawable backgroundDrawableBoolean = ll_boolean.getBackground();
          backgroundDrawableBoolean.setTint(
              ColorUtils.getColor(activity, com.google.android.material.R.attr.colorOnSurface));
          backgroundDrawableBoolean.setTintMode(PorterDuff.Mode.SRC_IN);
          ll_boolean.setBackground(backgroundDrawableBoolean);
          ll_boolean.setAlpha(0.3f);
          if (((ComplexBlockContent) blockContent.get(i)).getBlock() != null) {
            BlockDefaultView blockView = new BlockDefaultView(activity);
            blockView.setLanguage(language);
            blockView.setEnableEdit(enableEdit);
            try {
              Block block = ((ComplexBlockContent) blockContent.get(i)).getBlock().clone();
              blockView.setBlock(block);
            } catch (CloneNotSupportedException e) {
              blockView.setBlock(new Block());
            }
            ll_boolean.addView(blockView);
          }
          view.addView(ll_boolean, view.getChildCount());
        }
      } else if (blockContent.get(i) instanceof BlockContent) {
        TextView tvTextContent = new TextView(view.getContext());
        tvTextContent.setSingleLine(true);
        tvTextContent.setText(blockContent.get(i).getText());
        tvTextContent.setTextColor(
            ColorUtils.getColor(activity, com.google.android.material.R.attr.colorSurface));
        tvTextContent.setTextSize(12);
        view.addView(tvTextContent, view.getChildCount() - 1);
      }
    }
  }

  public class SourceContentClickListener implements View.OnClickListener {
    public TextView tvTextContent;
    public SourceContent blockContent;
    public Activity activity;
    public String language;
    public LinearLayout ll_source;

    public SourceContentClickListener(
        TextView param1,
        SourceContent param2,
        Activity param3,
        String param4,
        LinearLayout param5) {
      tvTextContent = param1;
      blockContent = param2;
      activity = param3;
      language = param4;
      ll_source = param5;
    }

    @Override
    public void onClick(View view) {
      if (activity instanceof EventEditorActivity) {
        EventEditorActivity mEventEditorActivity = (EventEditorActivity) activity;
        mEventEditorActivity.showSection(4);
        AceEditorColors aceEditorColors = new AceEditorColors();
        aceEditorColors.setEditorBackground("#00000000");
        aceEditorColors.setActiveLineColor("#0000002d");
        aceEditorColors.setGutterActiveLineColor("#0000002d");
        aceEditorColors.setGutterBackground("#00000000");
        aceEditorColors.setGutterTextColor(
            String.format(
                "#%06X",
                (0xFFFFFF
                    & ColorUtils.getColor(
                        activity, com.google.android.material.R.attr.colorOnSurfaceVariant))));
        aceEditorColors.apply(activity);
        mEventEditorActivity.binding.codeEditor.setEditor(CodeEditorLayout.AceCodeEditor);
        mEventEditorActivity.binding.codeEditor.setCode(blockContent.getValue());
        mEventEditorActivity.binding.codeEditor.setTheme(Themes.AceEditorTheme.Light.Default);
        mEventEditorActivity.binding.codeEditor.setLanguageMode(language);
        mEventEditorActivity.binding.done.setOnClickListener(
            (view2) -> {
              mEventEditorActivity.binding.codeEditor.getCode(
                  new EditorListeners() {

                    @Override
                    public void onReceviedCode(String code) {
                      mEventEditorActivity.runOnUiThread(
                          () -> {
                            mEventEditorActivity.showSection(3);
                            blockContent.setValue(code);
                            tvTextContent.setText(Utils.setWordLimitOnString(50, code));
                            updateContentPaddingWithText(tvTextContent, ll_source);
                          });
                    }
                  });
            });
        mEventEditorActivity.binding.cancel.setOnClickListener(
            (view2) -> {
              mEventEditorActivity.runOnUiThread(
                  () -> {
                    mEventEditorActivity.showSection(3);
                  });
            });
      }
    }
  }

  public static void updateContentPaddingWithText(TextView tv, LinearLayout ll) {
    if (tv.getText().length() == 0) {
      ll.setPadding(25, 0, 25, 0);
    } else {
      ll.setPadding(8, 0, 8, 0);
    }
  }
}
