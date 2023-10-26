package com.dragon.ide.utils.eventeditor;

import builtin.blocks.BuiltInBlocks;
import static com.dragon.ide.utils.Environments.BLOCKS;

import android.app.Activity;
import com.dragon.ide.objects.BlocksHolder;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BlocksListLoader {

  public void loadBlocks(Activity activity, Progress progress) {
    Executor executor = Executors.newSingleThreadExecutor();
    executor.execute(
        () -> {
          ArrayList<BlocksHolder> blocksHolder = new ArrayList<BlocksHolder>();

          ArrayList<BlocksHolder> builtInBlock = BuiltInBlocks.getBuiltInBlocksHolder();

          for (int i = 0; i < builtInBlock.size(); ++i) {
            blocksHolder.add(builtInBlock.get(i));
          }

          if (BLOCKS.exists()) {
            try {
              FileInputStream fis = new FileInputStream(BLOCKS);
              ObjectInputStream ois = new ObjectInputStream(fis);
              Object obj = ois.readObject();
              if (obj instanceof ArrayList) {
                for (int i = 0; i < ((ArrayList<BlocksHolder>) obj).size(); ++i) {
                  blocksHolder.add(((ArrayList<BlocksHolder>) obj).get(i));
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
