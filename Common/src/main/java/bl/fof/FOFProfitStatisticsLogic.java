package bl.fof;

import beans.PriceInfo;
import beans.ProfitStatisticsInfo;
import exception.ObjectNotFoundException;
import exception.ParameterException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Daniel on 2016/8/26.
 */

/**
 * 回报统计
 */
public interface FOFProfitStatisticsLogic extends Remote {
    /**
     * 设置区间开始时间
     *
     * @throws ParameterException
     */
    void setStartDate() throws ParameterException, RemoteException;

    /**
     * 设置区间结束时间
     *
     * @throws ParameterException
     */
    void setEndDate() throws ParameterException, RemoteException;

    /**
     * 设置业绩基准，大盘指数等
     *
     * @param indexCode
     * @throws RemoteException
     * @throws ObjectNotFoundException
     */
    void setProformanceBase(String indexCode) throws RemoteException, ObjectNotFoundException;

    void setUnitType() throws RemoteException;

    /**
     * 获得回报统计展示的信息
     * @return
     * @throws RemoteException
     */
    ProfitStatisticsInfo getProfitStatisticsInfo() throws RemoteException;

    /**
     * 获得画图需要的信息
     * @return
     * @throws RemoteException
     */
    List<PriceInfo> getPriceInfo() throws RemoteException;
}