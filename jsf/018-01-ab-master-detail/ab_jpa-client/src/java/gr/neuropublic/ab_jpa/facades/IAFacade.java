package gr.neuropublic.ab_jpa.facades;

import gr.neuropublic.base.IFacade;
import gr.neuropublic.ab_jpa.entities.*;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.Date;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.persistence.Query;

public interface IAFacade extends IFacade<A> {

    @Local
    public interface ILocal extends IAFacade {}

    @Remote
    public interface IRemote extends IAFacade {}

    public A initRow();

    public List<A> findAll();
    public int delAll();
    public A findById(Integer id);
    public int delById(Integer id);
    public List<A> findByA1(String a1);
    public int delByA1(String a1);

}
