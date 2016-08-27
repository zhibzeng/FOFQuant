package blimpl;

import beans.CodeName;
import beans.ConstParameter;
import beans.FundInfo;
import beans.FundQuickInfo;
import bl.BaseInfoLogic;
import dataservice.BaseInfoDataService;
import dataserviceimpl.DataServiceController;
import exception.ObjectNotFoundException;
import util.NumberOpe;
import util.SectorType;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel on 2016/8/15.
 */
public class BaseInfoLogicImpl extends UnicastRemoteObject implements BaseInfoLogic {

    private static BaseInfoLogic instance;

    private BaseInfoDataService baseInfoDataService;

    private BaseInfoLogicImpl() throws RemoteException {
        baseInfoDataService = DataServiceController.getBaseInfoDataService();
    }

    public static BaseInfoLogic getInstance() {
        if (instance == null)
            try {
                instance = new BaseInfoLogicImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        return instance;
    }

    @Override
    public List<String> getFundCodes() throws RemoteException {
        return baseInfoDataService.getAllCodes();
    }

    @Override
    public List<CodeName> fuzzySearch(String keyword) throws RemoteException {
        return baseInfoDataService.fuzzySearch(keyword);
    }

    @Override
    public FundInfo getFundBaseInfo(String code) throws RemoteException, ObjectNotFoundException {
        return Converter.convertFundInfo(baseInfoDataService.getFundInfo(code));
    }

    @Override
    public List<FundQuickInfo> getFundQuickInfo(String sectorId) throws RemoteException, ObjectNotFoundException {
        List<String> codes = null;
        for (String str : SectorType.COMPONENT_INFO.keySet()) {
            if (sectorId.equals(str)) {
                codes = new ArrayList<>();
                for (String componentId : SectorType.COMPONENT_INFO.get(str)) {
                    codes.addAll(baseInfoDataService.getSectorCodes(componentId));
                }
            }
        }
        if (codes == null)
            codes = baseInfoDataService.getSectorCodes(sectorId);
        List<FundQuickInfo> infos = new ArrayList<>();
        for (String code : codes) {
            try {
                FundQuickInfo quickInfo = Converter.convertFundQuickInfo(baseInfoDataService
                        .getFundQuickInfo(code));
                NumberOpe.controlDecimal(quickInfo, 2);
                infos.add(quickInfo);
            } catch (ObjectNotFoundException e) {
                e.printStackTrace();
                continue;
            }
        }
        return infos;
    }

    @Override
    public List<String> getAllSectorType(){
        List<String> sectorTypes=null;
        sectorTypes.add(SectorType.BOND_TYPE);
        sectorTypes.add(SectorType.INDEX_TYPE);
        sectorTypes.add(SectorType.ETF_TYPE);
//        sectorTypes.add(SectorType.FIX_PROFIT_TYPE);
        sectorTypes.add(SectorType.GUARANTEED_TYPE);
        sectorTypes.add(SectorType.LOF_TYPE);
        sectorTypes.add(SectorType.MIX_TYPE);
        sectorTypes.add(SectorType.MONEY_MARKET_TYPE);
        sectorTypes.add(SectorType.OPEN_TYPE);
//        sectorTypes.add(SectorType.OTHER_TYPE);
        sectorTypes.add(SectorType.QDII_TYPE);
//        sectorTypes.add(SectorType.RIGHTS_TYPE);
        sectorTypes.add(SectorType.STOCK_TYPE);

        return sectorTypes;
    }

    @Override
    public ConstParameter getConstaParameteer() throws RemoteException {
        return Converter.convertConstParameter(baseInfoDataService.getConstParameter());
    }
}
