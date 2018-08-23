package com.ry.fu.esb.jpush.model;

import java.util.List;

/**
 * Created by Yongjia Liang on 2017/12/11 18:23.
 */

public class BaseResponseList<T>
{
    private int total;
    private List<T> list;

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public List<T> getList()
    {
        return list;
    }

    public void setList(List<T> list)
    {
        this.list = list;
    }
}
