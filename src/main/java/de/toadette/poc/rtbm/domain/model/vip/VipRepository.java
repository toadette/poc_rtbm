package de.toadette.poc.rtbm.domain.model.vip;

/**
 * Created by Melanie on 12.09.2016.
 */
public interface VipRepository {

    Vip getVipByUserId(int userId) throws VipNotFoundException;
}
