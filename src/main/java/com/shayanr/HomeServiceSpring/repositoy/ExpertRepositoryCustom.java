package com.shayanr.HomeServiceSpring.repositoy;

import com.shayanr.HomeServiceSpring.entity.business.CustomerOrder;


import java.util.List;

public interface ExpertRepositoryCustom {

    List<CustomerOrder> seeOrdersByStatus(Integer expertId);
}
