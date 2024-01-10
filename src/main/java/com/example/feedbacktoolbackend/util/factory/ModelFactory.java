package com.example.feedbacktoolbackend.util.factory;

public interface ModelFactory<BusinessModel, DataEntity> {
    DataEntity convertToDataEntity(BusinessModel businessModel);
    BusinessModel createBusinessModel(DataEntity dataEntity);
}
