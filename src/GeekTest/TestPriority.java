package GeekTest;


public enum TestPriority {

    RUN_BEFORE(Integer.MIN_VALUE, true),
    RUN_0(0, false),
    RUN_1(1, false),
    RUN_2(2, false),
    RUN_3(3, false),
    RUN_4(4, false),
    RUN_5(5, false),
    RUN_6(6, false),
    RUN_7(7, false),
    RUN_8(8, false),
    RUN_9(9, false),
    RUN_AFTER(Integer.MAX_VALUE, true);

    private int order;
    private boolean runOnce;

    TestPriority(int order, boolean runOnce){
        this.order = order;
        this.runOnce = runOnce;
    }

    public int getOrder() {
        return order;
    }

    public boolean isRunOnce() {
        return runOnce;
    }
}


