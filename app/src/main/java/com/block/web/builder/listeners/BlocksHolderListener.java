package com.block.web.builder.listeners;

import com.block.web.builder.objects.BlocksHolder;

public interface BlocksHolderListener {
  void onBlockHolderCreate(BlocksHolder holder);

  void onBlocksHolderFailedToCreate(String error);
}
