package com.block.web.builder.core;

import android.util.Log;
import com.block.web.builder.core.blockcontent.BooleanContent;
import com.block.web.builder.core.blockcontent.SourceContent;
import com.block.web.builder.core.utils.CodeReplacer;
import java.io.Serializable;
import java.util.ArrayList;

public class ComplexBlock extends Block implements Serializable, Cloneable {
  public static final long serialVersionUID = 428383839L;
  private ArrayList<Block> blocks;

  public ArrayList<Block> getBlocks() {
    if (blocks != null) {
      return (ArrayList<Block>) this.blocks.clone();
    }
    return new ArrayList<Block>();
  }

  public void setBlocks(ArrayList<Block> blocks) {
    this.blocks = blocks;
  }

  @Override
  public String getCode() {
    String blockRawCode = new String(getRawCode());
    if (getBlockContent().size() != 0) {
      for (int i = 0; i < getBlockContent().size(); ++i) {
        if (getBlockContent().get(i) instanceof ComplexBlockContent) {
          blockRawCode =
              blockRawCode.replaceAll(
                  CodeReplacer.getReplacer(
                      ((ComplexBlockContent) getBlockContent().get(i)).getId()),
                  ((ComplexBlockContent) getBlockContent().get(i)).getCode());
        }
      }
    }
    StringBuilder blockListCode = new StringBuilder();
    for (int i = 0; i < getBlocks().size(); ++i) {
      if (i != 0) {
        blockListCode.append("\n");
      }
      if (getBlocks().get(i) instanceof DoubleComplexBlock) {
        blockListCode.append(((DoubleComplexBlock) getBlocks().get(i)).getCode());
      } else {
        if (getBlocks().get(i) instanceof ComplexBlock) {
          blockListCode.append(((ComplexBlock) getBlocks().get(i)).getCode());
        } else {
          if (getBlocks().get(i) instanceof Block) {
            blockListCode.append(getBlocks().get(i).getCode());
          }
        }
      }
    }
    String innerBlockCode = new String(blockListCode.toString());

    // Formatter

    String[] lines = blockRawCode.split("\n");
    StringBuilder mainCode = new StringBuilder();

    for (int i = 0; i < lines.length; ++i) {
      String line = new String(lines[i]);
      if (line.contains(CodeReplacer.getReplacer("complexBlockContent"))) {
        String[] innerBlockCodeLines = new String(innerBlockCode).split("\n");
        StringBuilder innerBlockCodeSB = new StringBuilder();
        for (int i2 = 0; i2 < innerBlockCodeLines.length; ++i2) {
          if (i2 != 0) {
            innerBlockCodeSB.append("\n");
            innerBlockCodeSB.append("\t");
          }
          innerBlockCodeSB.append(innerBlockCodeLines[i2]);
        }
        line =
            line.replaceAll(
                CodeReplacer.getReplacer("complexBlockContent"), innerBlockCodeSB.toString());
      }
      if (i != 0) {
        mainCode.append("\n");
      }
      mainCode.append(line);
    }

    blockRawCode = mainCode.toString();
    blockRawCode = CodeReplacer.removeBlockWebBuilderString(blockRawCode);
    return blockRawCode;
  }

  @Override
  public ComplexBlock clone() throws CloneNotSupportedException {
    ComplexBlock mComplexBlock = new ComplexBlock();
    String mColor;
    if (getColor() != null) {
      mColor = new String(getColor());
    } else {
      mColor = new String("");
    }
    String mName;
    if (getName() != null) {
      mName = new String(getName());
    } else {
      mName = new String("");
    }
    ArrayList<BlockContent> mBlockContent;
    if (getBlockContent() != null) {
      mBlockContent = new ArrayList<BlockContent>();
      for (int i = 0; i < getBlockContent().size(); ++i) {
        if (getBlockContent().get(i) instanceof ComplexBlockContent) {
          if (getBlockContent().get(i) instanceof SourceContent) {
            mBlockContent.add(((SourceContent) getBlockContent().get(i)).clone());
          } else if (getBlockContent().get(i) instanceof BooleanContent) {
            mBlockContent.add(((BooleanContent) getBlockContent().get(i)).clone());
          }
        } else if (getBlockContent().get(i) instanceof BlockContent) {
          mBlockContent.add(((BlockContent) getBlockContent().get(i)).clone());
        }
      }
    } else {
      mBlockContent = new ArrayList<BlockContent>();
    }
    int mBlockType;
    if (getBlockType() != 0) {
      mBlockType = new Integer(getBlockType());
    } else {
      mBlockType = 0;
    }
    String mRawCode;
    if (getRawCode() != null) {
      mRawCode = new String(getRawCode());
    } else {
      mRawCode = new String("");
    }
    String mReturns;
    if (getReturns() != null) {
      mReturns = new String(getReturns());
    } else {
      mReturns = new String("");
    }
    ArrayList<Block> mBlocks;
    if (getBlocks() != null) {
      mBlocks = new ArrayList<Block>();
      for (int i = 0; i < getBlocks().size(); ++i) {
        if (getBlocks().get(i) instanceof DoubleComplexBlock) {
          mBlocks.add(((DoubleComplexBlock) getBlocks().get(i)).clone());
        } else if (getBlocks().get(i) instanceof ComplexBlock) {
          mBlocks.add(((ComplexBlock) getBlocks().get(i)).clone());
        } else if (getBlocks().get(i) instanceof Block) {
          mBlocks.add(getBlocks().get(i).clone());
        }
      }
    } else {
      mBlocks = new ArrayList<Block>();
    }
    ArrayList<Block> mSideAttachableBlock;
    if (getSideAttachableBlock() != null) {
      mSideAttachableBlock = new ArrayList<Block>();
      for (int i = 0; i < getSideAttachableBlock().size(); ++i) {
        if (getSideAttachableBlock().get(i) instanceof DoubleComplexBlock) {
          mSideAttachableBlock.add(((DoubleComplexBlock) getSideAttachableBlock().get(i)).clone());
        } else if (getSideAttachableBlock().get(i) instanceof ComplexBlock) {
          mSideAttachableBlock.add(((ComplexBlock) getSideAttachableBlock().get(i)).clone());
        } else if (getSideAttachableBlock().get(i) instanceof Block) {
          mSideAttachableBlock.add(getSideAttachableBlock().get(i).clone());
        }
      }
    } else {
      mSideAttachableBlock = new ArrayList<Block>();
    }
    boolean mEnableSideAttachableBlock = new Boolean(getEnableSideAttachableBlock());
    mComplexBlock.setColor(mColor);
    mComplexBlock.setName(mName);
    mComplexBlock.setBlockContent(mBlockContent);
    mComplexBlock.setBlockType(mBlockType);
    mComplexBlock.setRawCode(mRawCode);
    mComplexBlock.setReturns(mReturns);
    mComplexBlock.setBlocks(mBlocks);
    mComplexBlock.setEnableSideAttachableBlock(mEnableSideAttachableBlock);
    mComplexBlock.setSideAttachableBlock(mSideAttachableBlock);
    return mComplexBlock;
  }
}
