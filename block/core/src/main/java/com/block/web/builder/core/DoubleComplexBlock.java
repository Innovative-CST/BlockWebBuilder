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

package com.block.web.builder.core;

import com.block.web.builder.core.blockcontent.BooleanContent;
import com.block.web.builder.core.blockcontent.NumberContent;
import com.block.web.builder.core.blockcontent.SourceContent;
import com.block.web.builder.core.utils.CodeReplacer;
import java.io.Serializable;
import java.util.ArrayList;

public class DoubleComplexBlock extends ComplexBlock implements Serializable, Cloneable {
  public static final long serialVersionUID = 428383841L;
  private ArrayList<Block> doubleComplexBlocks;
  private ArrayList<BlockContent> complexBlockContent;

  public ArrayList<Block> getDoubleComplexBlocks() {
    if (doubleComplexBlocks != null) {
      return (ArrayList<Block>) this.doubleComplexBlocks.clone();
    }
    return new ArrayList<Block>();
  }

  public void setDoubleComplexBlocks(ArrayList<Block> doubleComplexBlocks) {
    this.doubleComplexBlocks = doubleComplexBlocks;
  }

  @Override
  public String getCode() {
    String blockRawCode = new String(getRawCode());
    for (int i = 0; i < getBlockContent().size(); ++i) {
      if (getBlockContent().get(i) instanceof ComplexBlockContent) {
        blockRawCode =
            blockRawCode.replaceAll(
                CodeReplacer.getReplacer(((ComplexBlockContent) getBlockContent().get(i)).getId()),
                ((ComplexBlockContent) getBlockContent().get(i)).getValue());
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
    String innerBlockCode = blockListCode.toString();
    blockRawCode =
        blockRawCode.replaceAll(CodeReplacer.getReplacer("complexBlockContent"), innerBlockCode);

    for (int i = 0; i < getComplexBlockContent().size(); ++i) {
      if (getComplexBlockContent().get(i) instanceof ComplexBlockContent) {
        blockRawCode =
            blockRawCode.replaceAll(
                CodeReplacer.getReplacer(((ComplexBlockContent) getBlockContent().get(i)).getId()),
                ((ComplexBlockContent) getBlockContent().get(i)).getCode());
      }
    }

    StringBuilder complexBlockListCode = new StringBuilder();
    for (int i = 0; i < getDoubleComplexBlocks().size(); ++i) {
      if (i != 0) {
        complexBlockListCode.append("\n");
      }
      if (getDoubleComplexBlocks().get(i) instanceof DoubleComplexBlock) {
        complexBlockListCode.append(((DoubleComplexBlock) getBlocks().get(i)).getCode());
      } else {
        if (getDoubleComplexBlocks().get(i) instanceof ComplexBlock) {
          complexBlockListCode.append(((ComplexBlock) getBlocks().get(i)).getCode());
        } else {
          if (getDoubleComplexBlocks().get(i) instanceof Block) {
            complexBlockListCode.append(getBlocks().get(i).getCode());
          }
        }
      }
    }
    String doubleInnerBlockCode = complexBlockListCode.toString();
    blockRawCode =
        blockRawCode.replaceAll(
            CodeReplacer.getReplacer("doubleComplexBlockContent"), doubleInnerBlockCode);
    blockRawCode = CodeReplacer.removeBlockWebBuilderString(blockRawCode);
    return new String(blockRawCode);
  }

  public ArrayList<BlockContent> getComplexBlockContent() {
    return this.complexBlockContent;
  }

  public void setComplexBlockContent(ArrayList<BlockContent> conplexBlockContent) {
    this.complexBlockContent = complexBlockContent;
  }

  @Override
  public DoubleComplexBlock clone() throws CloneNotSupportedException {
    DoubleComplexBlock mDoubleComplexBlock = new DoubleComplexBlock();
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
    ArrayList<Block> mDoubleComplexBlocks;
    if (getDoubleComplexBlocks() != null) {
      mDoubleComplexBlocks = new ArrayList<Block>();
      for (int i = 0; i < getDoubleComplexBlocks().size(); ++i) {
        if (getDoubleComplexBlocks().get(i) instanceof DoubleComplexBlock) {
          mDoubleComplexBlocks.add(((DoubleComplexBlock) getDoubleComplexBlocks().get(i)).clone());
        } else if (getDoubleComplexBlocks().get(i) instanceof ComplexBlock) {
          mDoubleComplexBlocks.add(((ComplexBlock) getDoubleComplexBlocks().get(i)).clone());
        } else if (getDoubleComplexBlocks().get(i) instanceof Block) {
          mDoubleComplexBlocks.add(getDoubleComplexBlocks().get(i).clone());
        }
      }
    } else {
      mDoubleComplexBlocks = new ArrayList<Block>();
    }
    ArrayList<BlockContent> mComplexBlockContent;
    if (getComplexBlockContent() != null) {
      mComplexBlockContent = new ArrayList<BlockContent>();
      for (int i = 0; i < getComplexBlockContent().size(); ++i) {
        if (getComplexBlockContent().get(i) instanceof ComplexBlockContent) {
          if (getBlockContent().get(i) instanceof SourceContent) {
            mBlockContent.add(((SourceContent) getBlockContent().get(i)).clone());
          } else if (getBlockContent().get(i) instanceof BooleanContent) {
            mBlockContent.add(((BooleanContent) getBlockContent().get(i)).clone());
          } else if (getBlockContent().get(i) instanceof NumberContent) {
            mBlockContent.add(((NumberContent) getBlockContent().get(i)).clone());
          }
        } else if (getBlockContent().get(i) instanceof BlockContent) {
          mComplexBlockContent.add(((BlockContent) getComplexBlockContent().get(i)).clone());
        }
      }
    } else {
      mComplexBlockContent = new ArrayList<BlockContent>();
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
    mDoubleComplexBlock.setColor(mColor);
    mDoubleComplexBlock.setName(mName);
    mDoubleComplexBlock.setBlockContent(mBlockContent);
    mDoubleComplexBlock.setBlockType(mBlockType);
    mDoubleComplexBlock.setRawCode(mRawCode);
    mDoubleComplexBlock.setReturns(mReturns);
    mDoubleComplexBlock.setBlocks(mBlocks);
    mDoubleComplexBlock.setDoubleComplexBlocks(mDoubleComplexBlocks);
    mDoubleComplexBlock.setComplexBlockContent(mComplexBlockContent);
    mDoubleComplexBlock.setEnableSideAttachableBlock(mEnableSideAttachableBlock);
    mDoubleComplexBlock.setSideAttachableBlock(mSideAttachableBlock);
    return mDoubleComplexBlock;
  }
}
