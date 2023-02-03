/*
 * Copyright 2016 jeasonlzy(廖子尧)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package http.callback;



import java.io.Serializable;

import util.TimeUtil;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/28
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class QueryVoLzyResponse<T> implements Serializable {
    /**
     * @Fields serialVersionUID :TODO
     */
    private static final long serialVersionUID = 1L;

    /***
     * 请求状态
     ***/
    public boolean status;

    /***
     * 信息代码1、成功，-1异常
     ***/
    public int code;
    /***
     * 信息描述
     **/
    public String desc;

    /**
     * 需要交互的数据
     */
    public T data;
    /***
     * 请求次数,判断请求是否失效，避免copy重复请求
     ***/
    public String draw;

    /***
     * 加密字符串
     ***/
    public String nextSecret;

    /***
     * 回复的时间
     ***/
    public String time = TimeUtil.getNowStr();

    public long times = TimeUtil.getNowLong();

    public int  total;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public QueryVoLzyResponse() {
        code = -1;
        status = false;
    }

    public QueryVoLzyResponse(boolean status) {
        this.status = status;
    }

    public QueryVoLzyResponse(boolean status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;

    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDraw() {
        return draw;
    }

    public void setDraw(String draw) {
        this.draw = draw;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getNextSecret() {
        return nextSecret;
    }

    public void setNextSecret(String nextSecret) {
        this.nextSecret = nextSecret;
    }

    public void reSetTime() {
        this.setTimes(TimeUtil.getNowLong());
        this.setTime(TimeUtil.getNowStr());
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    @Override
    public String toString() {
        return "MsgRx{" +
                "status=" + status +
                ", code=" + code +
                ", desc='" + desc + '\'' +
                ", data=" + data +
                ", draw=" + draw +
                ", nextSecret='" + nextSecret + '\'' +
                ", time='" + time + '\'' +
                ", times=" + times +
                '}';
    }
}
