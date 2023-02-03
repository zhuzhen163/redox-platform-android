package util.event;

/**
 * Created by Administrator on 2018/4/23 0023.
 * 注册TokenEventBus  Token失效逻辑处理
 */

public class TokenEventBus {
    private int message;
    private Object obg;

    public TokenEventBus(int message) {
        this.message = message;
    }

    public TokenEventBus(int message, Object obg) {
        this.message = message;
        this.obg = obg;
    }

    public Object getObg() {
        return obg;
    }

    public void setObg(Object obg) {
        this.obg = obg;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}
