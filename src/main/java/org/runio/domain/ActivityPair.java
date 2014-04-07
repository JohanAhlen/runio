package org.runio.domain;

public class ActivityPair<T, S> {

    private T leftHandActivity;
    private S rightHandActivity;

    public ActivityPair(T leftHandActivity, S rightHandActivity) {
        this.leftHandActivity = leftHandActivity;
        this.rightHandActivity = rightHandActivity;
    }

    public T getLeftHandActivity() {
        return leftHandActivity;
    }

    public S getRightHandActivity() {
        return rightHandActivity;
    }
}
