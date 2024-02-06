package com.block.web.builder.utils;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.block.web.builder.R;
import com.block.web.builder.core.Block;
import com.block.web.builder.core.BlockContent;
import com.block.web.builder.core.ComplexBlockContent;
import com.block.web.builder.core.blockcontent.BooleanContent;
import com.block.web.builder.core.blockcontent.NumberContent;
import com.block.web.builder.core.blockcontent.SourceContent;
import com.block.web.builder.ui.activities.EventEditorActivity;
import com.block.web.builder.ui.activities.SettingActivity;
import com.block.web.builder.ui.view.blocks.BlockDefaultView;
import editor.tsd.editors.ace.AceEditorColors;
import editor.tsd.editors.sora.lang.textmate.provider.TextMateProvider;
import editor.tsd.tools.EditorListeners;
import editor.tsd.tools.Themes;
import editor.tsd.widget.CodeEditorLayout;
import io.github.rosemoe.sora.langs.textmate.registry.FileProviderRegistry;
import io.github.rosemoe.sora.langs.textmate.registry.provider.AssetsFileResolver;
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
          tvTextContent.setTextSize(11);
          tvTextContent.setTextColor(
              ColorUtils.getColor(activity, com.google.android.material.R.attr.colorOnSurface));
          ll_source.addView(tvTextContent);

          final LinearLayout.LayoutParams layoutParams =
              new LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
          int startMargin = 8;
          int endMargin = 8;
          layoutParams.setMarginStart(startMargin);
          layoutParams.setMarginEnd(endMargin);
          ll_source.setLayoutParams(layoutParams);

          view.addView(ll_source);

          final SourceContent sc = (SourceContent) blockContent.get(i);

          if (enableEdit) {
            ll_source.setOnClickListener(
                new BlockContentLoader()
                .new SourceContentClickListener(tvTextContent, sc, activity, language, ll_source));
          }
        } else if (blockContent.get(i) instanceof BooleanContent) {
          final BooleanContent cbc = ((BooleanContent) blockContent.get(i));
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
        } else if (blockContent.get(i) instanceof NumberContent) {
          final NumberContent cbc = ((NumberContent) blockContent.get(i));
          final LinearLayout ll_number =
              new LinearLayout(view.getContext()) {
                @Override
                public void addView(View v) {
                  super.addView(v);
                  if (v instanceof BlockDefaultView) {
                    cbc.setBlock(((BlockDefaultView) v).getBlock());
                  }
                }

                @Override
                public void addView(View v, int index) {
                  super.addView(v, index);
                  if (v instanceof BlockDefaultView) {
                    cbc.setBlock(((BlockDefaultView) v).getBlock());
                  }
                }

                @Override
                public void removeView(View v) {
                  super.removeView(v);
                  if (v instanceof BlockDefaultView) {
                    cbc.setValue("");
                  }
                }
              };
          ll_number.setTag(((NumberContent) blockContent.get(i)).getAcceptance());
          ll_number.setBackgroundResource(R.drawable.number);
          Drawable backgroundDrawableNumber = ll_number.getBackground();
          backgroundDrawableNumber.setTint(
              ColorUtils.getColor(activity, com.google.android.material.R.attr.colorSurface));
          backgroundDrawableNumber.setTintMode(PorterDuff.Mode.SRC_IN);
          ll_number.setBackground(backgroundDrawableNumber);
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
            ll_number.addView(blockView);
          }
          view.addView(ll_number, view.getChildCount());
        }
      } else if (blockContent.get(i) instanceof BlockContent) {
        TextView tvTextContent = new TextView(view.getContext());
        tvTextContent.setSingleLine(true);
        tvTextContent.setText(blockContent.get(i).getText());
        tvTextContent.setTextColor(
            ColorUtils.getColor(activity, com.google.android.material.R.attr.colorSurface));
        tvTextContent.setTextSize(11);
        final LinearLayout.LayoutParams layoutParams =
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int startMargin = 2;
        int endMargin = 2;
        layoutParams.setMarginStart(startMargin);
        layoutParams.setMarginEnd(endMargin);
        tvTextContent.setLayoutParams(layoutParams);
        view.addView(tvTextContent, view.getChildCount());
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

        boolean useSoraEditor = false;
        if (mEventEditorActivity.getPreferences() != null) {
          if ((boolean)
              SettingActivity.getPreferencesValue(
                  mEventEditorActivity.getPreferences(), "Use Sora Editor", false)) {
            useSoraEditor = true;
          }
        }

        if (useSoraEditor) {
          FileProviderRegistry.getInstance()
              .addFileProvider(new AssetsFileResolver(mEventEditorActivity.getAssets()));
          try {
            TextMateProvider.loadGrammars();
          } catch (Exception e) {
            Toast.makeText(mEventEditorActivity, e.getMessage(), Toast.LENGTH_LONG).show();
          }

          mEventEditorActivity.binding.codeEditor.setEditor(CodeEditorLayout.SoraCodeEditor);
          mEventEditorActivity.binding.codeEditor.setTheme(Themes.SoraEditorTheme.Light.Default);
        } else {
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
          mEventEditorActivity.binding.codeEditor.setTheme(Themes.AceEditorTheme.Light.Default);
        }

        mEventEditorActivity.binding.codeEditor.setCode(blockContent.getValue());
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
