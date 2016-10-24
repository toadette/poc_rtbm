package de.toadette.poc.rtbm.port.adapter;

import org.junit.Before;
import org.junit.Test;

import de.toadette.poc.rtbm.domain.model.vip.Vip;
import de.toadette.poc.rtbm.domain.model.vip.VipNotFoundException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FakeVipRepositoryTest {

    private FakeVipRepository fakeVipRepository;

    @Before
    public void setUp() throws Exception {
        fakeVipRepository=new FakeVipRepository();

    }

    @Test
    public void getVipByUserName_ShouldReturnLaxo() throws Exception {
        Vip vipByUserName = fakeVipRepository.getVipByUserId(1);
        assertThat(vipByUserName.getPostsCount(),is(10));
        assertThat(vipByUserName.getUsername(),is("LaxoLax"));
    }

    @Test
    public void getVipByUserName_ShouldReturnLaxoline() throws Exception {
        Vip vipByUserName = fakeVipRepository.getVipByUserId(2);
        assertThat(vipByUserName.getPostsCount(),is(100));
        assertThat(vipByUserName.getUsername(),is("LaxolineLax"));
    }
    @Test(expected = VipNotFoundException.class)
    public void getVipByUserName_ShouldReturnVipNotFound() throws Exception {
        fakeVipRepository.getVipByUserId(3);
    }

}