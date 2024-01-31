package com.block.web.builder.utils;

import com.block.web.builder.BuildConfig;
import com.block.web.builder.core.Block;
import editor.tsd.tools.Language;
import java.util.ArrayList;

public class FilterBlocks {
  public static ArrayList<Block> filterBlocksWithTag(ArrayList<Block> blocks, String language) {
    ArrayList<Block> filteredBlocks = new ArrayList<Block>();
    for (int i = 0; i < blocks.size(); ++i) {
      boolean addHolder = false;
      boolean forHtml = false;
      boolean forCss = false;
      boolean forJavaScript = false;
      boolean forDeveloper = false;
      boolean forDeveloperOnly = false;

      for (int blockTagIndex = 0; blockTagIndex < blocks.get(i).getTags().length; ++blockTagIndex) {
        if (blocks.get(i).getTags()[blockTagIndex].equals("developerOnly")) {
          forDeveloperOnly = true;
        }
        if (blocks.get(i).getTags()[blockTagIndex].equals("developer")) {
          forDeveloper = true;
        }
        if (blocks.get(i).getTags()[blockTagIndex].equals(Language.HTML)) {
          forHtml = true;
        }
        if (blocks.get(i).getTags()[blockTagIndex].equals(Language.CSS)) {
          forCss = true;
        }
        if (blocks.get(i).getTags()[blockTagIndex].equals(Language.JavaScript)) {
          forJavaScript = true;
        }
      }

      if (forHtml) {
        if (language.equals(Language.HTML)) {
          addHolder = true;
        }
      }

      if (forCss) {
        if (language.equals(Language.CSS)) {
          addHolder = true;
        }
      }

      if (forJavaScript) {
        if (language.equals(Language.JavaScript)) {
          addHolder = true;
        }
      }

      if (forDeveloper) {
        if (BuildConfig.enableDeveloperBlocks) {
          addHolder = true;
        }
      }

      if (forDeveloperOnly) {
        if (BuildConfig.enableDeveloperBlocks) {
          addHolder = true;
        } else {
          addHolder = false;
        }
      }

      if (addHolder) {
        filteredBlocks.add(blocks.get(i));
      }
    }
    return filteredBlocks;
  }
}
