/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.modules.api.entity;


import com.google.common.collect.Lists;
import com.jeeplus.modules.keyword_seeker.entity.CdsrKeywordSeeker;

import java.util.List;

/**
 * 爬取的域名Entity
 *
 * @author 尹彬
 * @version 2019-03-26
 */
public class CdsKeywordSelected extends CdsrKeywordSeeker {

    private static final long serialVersionUID = 1L;

    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}