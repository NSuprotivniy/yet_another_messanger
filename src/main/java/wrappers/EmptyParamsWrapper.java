package wrappers;

public class EmptyParamsWrapper implements ParamsWrapper {
    private EmptyParamsWrapper params = null;

    public EmptyParamsWrapper getParams() {
        return params;
    }

    public void setParams(EmptyParamsWrapper params) {
        this.params = params;
    }
}
