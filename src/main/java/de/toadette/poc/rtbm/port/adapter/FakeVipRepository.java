package de.toadette.poc.rtbm.port.adapter;

import de.toadette.poc.rtbm.domain.model.vip.BasicVip;
import de.toadette.poc.rtbm.domain.model.vip.Vip;
import de.toadette.poc.rtbm.domain.model.vip.VipNotFoundException;
import de.toadette.poc.rtbm.domain.model.vip.VipRepository;

public class FakeVipRepository implements VipRepository {

    @Override
    public Vip getVipByUserId(int id) throws VipNotFoundException {
        if (id == 1) {
            return new BasicVip("LaxoLax", 10);
        }
        if (id == 2) {
            return new BasicVip("LaxolineLax", 100);
        }
        throw new VipNotFoundException();
    }
}
