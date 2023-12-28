package com.dragon.ide.utils.eventeditor;

import static com.dragon.ide.utils.Environments.BLOCKS;

import android.app.Activity;
import builtin.blocks.BuiltInBlocks;
import com.dragon.ide.BuildConfig;
import com.dragon.ide.objects.BlocksHolder;
import editor.tsd.tools.Language;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BlocksListLoader {

  public void loadBlocks(Activity activity, String language, Progress progress) {
    Executor executor = Executors.newSingleThreadExecutor();
    executor.execute(
        () -> {
          ArrayList<BlocksHolder> blocksHolder = new ArrayList<BlocksHolder>();

          ArrayList<BlocksHolder> builtInBlock = BuiltInBlocks.getBuiltInBlocksHolder();

          for (int i = 0; i < builtInBlock.size(); ++i) {
            boolean addHolder = false;
            boolean forHtml = false;
            boolean forCss = false;
            boolean forJavaScript = false;
            boolean forDeveloper = false;
            boolean forDeveloperOnly = false;

            for (int blockTagIndex = 0;
                blockTagIndex < builtInBlock.get(i).getTags().length;
                ++blockTagIndex) {
              if (builtInBlock.get(i).getTags()[blockTagIndex].equals("developerOnly")) {
                forDeveloperOnly = true;
              }
              if (builtInBlock.get(i).getTags()[blockTagIndex].equals("developer")) {
                forDeveloper = true;
              }
              if (builtInBlock.get(i).getTags()[blockTagIndex].equals(Language.HTML)) {
                forHtml = true;
              }
              if (builtInBlock.get(i).getTags()[blockTagIndex].equals(Language.CSS)) {
                forCss = true;
              }
              if (builtInBlock.get(i).getTags()[blockTagIndex].equals(Language.JavaScript)) {
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
              blocksHolder.add(builtInBlock.get(i));
            }
          }

          if (BLOCKS.exists()) {
            try {
              FileInputStream fis = new FileInputStream(BLOCKS);
              ObjectInputStream ois = new ObjectInputStream(fis);
              Object obj = ois.readObject();
              if (obj instanceof ArrayList) {
                for (int i = 0; i < ((ArrayList<BlocksHolder>) obj).size(); ++i) {
                  boolean forHtml = false;
                  boolean forCss = false;
                  boolean forJavaScript = false;
                  boolean forDeveloper = false;
                  boolean forDeveloperOnly = false;
                  for (int blockTagIndex = 0;
                      blockTagIndex < ((ArrayList<BlocksHolder>) obj).get(i).getTags().length;
                      ++blockTagIndex) {
                    if (((ArrayList<BlocksHolder>) obj)
                        .get(i)
                        .getTags()[blockTagIndex]
                        .equals("developerOnly")) {
                      forDeveloperOnly = true;
                    }
                    if (((ArrayList<BlocksHolder>) obj)
                        .get(i)
                        .getTags()[blockTagIndex]
                        .equals("developer")) {
                      forDeveloper = true;
                    }
                    if (((ArrayList<BlocksHolder>) obj)
                        .get(i)
                        .getTags()[blockTagIndex]
                        .equals(Language.HTML)) {
                      forHtml = true;
                    }
                    if (((ArrayList<BlocksHolder>) obj)
                        .get(i)
                        .getTags()[blockTagIndex]
                        .equals(Language.CSS)) {
                      forCss = true;
                    }
                    if (((ArrayList<BlocksHolder>) obj)
                        .get(i)
                        .getTags()[blockTagIndex]
                        .equals(Language.JavaScript)) {
                      forJavaScript = true;
                    }
                  }

                  boolean addHolder = false;

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
                    blocksHolder.add(((ArrayList<BlocksHolder>) obj).get(i));
                  }
                }
              } else {
              }
              fis.close();
              ois.close();
            } catch (Exception e) {
            }
          } else {
          }
          activity.runOnUiThread(
              () -> {
                progress.onCompleteLoading(blocksHolder);
              });
        });
  }

  public interface Progress {
    void onCompleteLoading(ArrayList<BlocksHolder> holder);
  }
}
