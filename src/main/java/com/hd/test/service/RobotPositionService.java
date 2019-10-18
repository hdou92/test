package com.hd.test.service;

import com.hd.test.common.ZStringUtils;
import com.hd.test.consts.TestConsts;
import com.hd.test.entity.Office;
import com.hd.test.entity.OfficeBuilding;
import com.hd.test.entity.TestPosition;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RobotPositionService extends BaseService<TestPosition> {

    public RobotPositionService() {
        addPosition();
    }

    public void addPosition(){
        for (int i = 1; i <= 3; i++) {
            save(new TestPosition("门口" + i, new OfficeBuilding(TestConsts.BUILDING_ID1), new Office(TestConsts.OFFICE_ID1), UUID.randomUUID().toString(), TestConsts.ROBOTMODEL_E2));
        }
        for (int i = 1; i <= 3; i++) {
            save(new TestPosition("门口" + i, new OfficeBuilding(TestConsts.BUILDING_ID2), new Office(TestConsts.OFFICE_ID1), UUID.randomUUID().toString(), TestConsts.ROBOTMODEL_E2));
        }
        for (int i = 1; i <= 5; i++) {
            save(new TestPosition("门口" + i, new OfficeBuilding(TestConsts.BUILDING_ID1), new Office(TestConsts.OFFICE_ID1), UUID.randomUUID().toString(), TestConsts.ROBOTMODEL_Y1));
        }

        for (int i = 1; i <= 2; i++) {
            save(new TestPosition("门口" + i, new OfficeBuilding(TestConsts.BUILDING_ID1), new Office(TestConsts.OFFICE_ID2), UUID.randomUUID().toString(), TestConsts.ROBOTMODEL_E2));
        }
        for (int i = 1; i <= 2; i++) {
            save(new TestPosition("门口" + i, new OfficeBuilding(TestConsts.BUILDING_ID2), new Office(TestConsts.OFFICE_ID2), UUID.randomUUID().toString(), TestConsts.ROBOTMODEL_E2));
        }
        for (int i = 1; i <= 4; i++) {
            save(new TestPosition("门口" + i, new OfficeBuilding(TestConsts.BUILDING_ID1), new Office(TestConsts.OFFICE_ID2), UUID.randomUUID().toString(), TestConsts.ROBOTMODEL_Y1));
        }
    }

    public List<TestPosition> getAllListByOfficeId(String officeId) {
        return getData().stream().filter(a -> ZStringUtils.isEquals(officeId, a.getOffice().getId())).collect(Collectors.toList());
    }
}
