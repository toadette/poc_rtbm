package de.toadette.poc.rtbm.domain.model.vip;


public class BasicVip implements Vip {

    private String userName;
    private int postsCount;

    public BasicVip(String userName, int postsCount) {
        this.userName = userName;
        this.postsCount = postsCount;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public int getPostsCount() {
        return postsCount;
    }
}
