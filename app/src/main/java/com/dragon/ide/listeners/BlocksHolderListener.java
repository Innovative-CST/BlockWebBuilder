package com.dragon.ide.listeners;

import com.dragon.ide.objects.BlocksHolder;

public interface BlocksHolderListener {
  void onBlockHolderCreate(BlocksHolder holder);

  void onBlocksHolderFailedToCreate(String error);
}
