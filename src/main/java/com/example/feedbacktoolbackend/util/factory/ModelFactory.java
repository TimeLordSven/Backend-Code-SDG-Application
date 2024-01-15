package com.example.feedbacktoolbackend.util.factory;

public interface ModelFactory<BusinessModel, DataEntity> {
    DataEntity createDataEntity (BusinessModel businessModel);
    BusinessModel createBusinessModel(DataEntity dataEntity);
}
