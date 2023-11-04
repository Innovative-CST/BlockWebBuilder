package com.dragon.ide.objects;

import android.util.Log;
import com.dragon.ide.objects.blockcontent.SourceContent;
import com.dragon.ide.utils.CodeReplacer;
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
                  ((ComplexBlockContent) getBlockContent().get(i)).getValue());
        }
      }
    }
    StringBuilder blockListCode = new StringBuilder();
    for (int i = 0; i < getBlocks().size(); ++i) {
      if (i != 0) {
        blockListCode.append("\n");
      }
      if (getBlocks().get(i) instanceof DoubleComplexBlock) {
        try {
          blockListCode.append(((DoubleComplexBlock) getBlocks().get(i)).clone().getCode());
        } catch (CloneNotSupportedException e) {
        }
      } else {
        if (getBlocks().get(i) instanceof ComplexBlock) {
          try {
            blockListCode.append(((ComplexBlock) getBlocks().get(i)).clone().getCode());
          } catch (CloneNotSupportedException e) {
          }
        } else {
          if (getBlocks().get(i) instanceof Block) {
            try {
              blockListCode.append(getBlocks().get(i).clone().getCode());
            } catch (CloneNotSupportedException e) {
            }
          }
        }
      }
    }
    String innerBlockCode = blockListCode.toString();
    blockRawCode =
        blockRawCode.replaceAll(CodeReplacer.getReplacer("complexBlockContent"), innerBlockCode);
    blockRawCode = CodeReplacer.removeDragonIDEString(blockRawCode);
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
    ArrayList<Object> mBlockContent;
    if (getBlockContent() != null) {
      mBlockContent = new ArrayList<Object>();
      for (int i = 0; i < getBlockContent().size(); ++i) {
        if (getBlockContent().get(i) instanceof ComplexBlockContent) {
          if (getBlockContent().get(i) instanceof SourceContent) {
            mBlockContent.add(((SourceContent) getBlockContent().get(i)).clone());
          }
        } else if (getBlockContent().get(i) instanceof BlockContent) {
          mBlockContent.add(((BlockContent) getBlockContent().get(i)).clone());
        }
      }
    } else {
      mBlockContent = new ArrayList<Object>();
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

    mComplexBlock.setColor(mColor);
    mComplexBlock.setName(mName);
    mComplexBlock.setBlockContent(mBlockContent);
    mComplexBlock.setBlockType(mBlockType);
    mComplexBlock.setRawCode(mRawCode);
    mComplexBlock.setReturns(mReturns);
    mComplexBlock.setBlocks(mBlocks);
    return mComplexBlock;
  }
}
