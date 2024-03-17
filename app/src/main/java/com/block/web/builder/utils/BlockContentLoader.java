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
import com.block.web.builder.ui.view.blocks.BlockDefaultView;
import com.block.web.builder.ui.view.blocks.BlockHint;
import com.block.web.builder.utils.preferences.PreferencesUtils;
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
                    setBackground(null);
                    cbc.setBlock(((BlockDefaultView) v).getBlock());
                  } else if (v instanceof BlockHint) {
                    setBackground(null);
                  }
                }

                @Override
                public void addView(View v, int index) {
                  super.addView(v, index);
                  if (v instanceof BlockDefaultView) {
                    setBackground(null);
                    cbc.setBlock(((BlockDefaultView) v).getBlock());
                  } else if (v instanceof BlockHint) {
                    setBackground(null);
                  }
                }

                @Override
                public void removeView(View v) {
                  super.removeView(v);
                  if (v instanceof BlockDefaultView) {
                    cbc.setValue("");

                    Drawable backgroundDrawableNumber =
                        getResources().getDrawable(R.drawable.number);
                    backgroundDrawableNumber.setTint(
                        ColorUtils.getColor(
                            activity, com.google.android.material.R.attr.colorSurface));
                    backgroundDrawableNumber.setTintMode(PorterDuff.Mode.SRC_IN);
                    setBackground(backgroundDrawableNumber);
                  } else if (v instanceof BlockHint) {
                    if (getChildCount() > 0) {
                      if (!(getChildAt(0) instanceof BlockDefaultView)) {
                        Drawable backgroundDrawableNumber =
                            getResources().getDrawable(R.drawable.number);
                        backgroundDrawableNumber.setTint(
                            ColorUtils.getColor(
                                activity, com.google.android.material.R.attr.colorSurface));
                        backgroundDrawableNumber.setTintMode(PorterDuff.Mode.SRC_IN);
                        setBackground(backgroundDrawableNumber);
                      }
                    } else {
                      Drawable backgroundDrawableNumber =
                          getResources().getDrawable(R.drawable.number);
                      backgroundDrawableNumber.setTint(
                          ColorUtils.getColor(
                              activity, com.google.android.material.R.attr.colorSurface));
                      backgroundDrawableNumber.setTintMode(PorterDuff.Mode.SRC_IN);
                      setBackground(backgroundDrawableNumber);
                    }
                  }
                }
              };
          ll_number.setTag(((NumberContent) blockContent.get(i)).getAcceptance());
          Drawable backgroundDrawableNumber =
              activity.getResources().getDrawable(R.drawable.number);
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
              PreferencesUtils.getPreferencesValue(
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
        mEventEditorActivity.binding.save.setOnClickListener(
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
