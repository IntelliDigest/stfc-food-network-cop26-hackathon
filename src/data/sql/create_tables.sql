#==> ../../data/agronomic/cropMaster.csv <==
#"crop_id";"crop_name";"crop_code";"variety_name"
#1;"GARLIC";"1";"BULB VEGETABLES"
#
#
#==> ../../data/agronomic/diseasePesticideMapping.csv <==
#"disease_pesticide_map_id";"crop_code";"disease_id";"pesticide_code";"min_dosage_per_ha";"max_dosage_per_ha";"min_dilute_water";"max_dilute_water";"waiting_period_days";"crop_stage"
#1;6;1;5;"666";"1000";"300";"500";"15";""
#
#==> ../../data/agronomic/fertilizerCropMap.csv <==
#"fertilizer_map_id";"fertilizer_code";"crop_id";"index_value";"nutrient_id"
#1;6;6;0.0;1
#
#==> ../../data/agronomic/fertilizerMaster.csv <==
#"fertilizer_id";"fertilizer_name";"fertilizer_code";"fertilizer_type";"brand_name";"company_name";"price";"quantity";"image_path"
#1;"MOP";"1";"";"Potash";"CIL";0.0;0.0;""
#
#==> ../../data/agronomic/pesticideMaster.csv <==
#"pesticide_id";"pesticide_name";"pesticide_code";"price";"quantity";"type";"iupa_number";"mfg_local";"company_name"
#1;"24-D Ethyl Ester Technical 97% min. ";1;0.0;0.0;"Herbicide";"45";"Y";"Insecticides India Ltd Delhi "


CREATE TABLE IF NOT EXISTS `cropMaster` (
    crop_id INTEGER NOT NULL,
    crop_name VARCHAR(255),
    crop_code VARCHAR(255),
    variety_name VARCHAR(255)
    PRIMARY KEY (crop_id)
);

CREATE TABLE IF NOT EXISTS `diseaseMaster` (
#==> ../../data/agronomic/diseaseMaster.csv <==
#"disease_id";"crop_id";"disease_name";"disease_name_telugu";"scientific_name";"image_path";"symptoms"
#1;6;"Stem Borer";"";"Scirpophaga incertulas";"";"Damage is caused by larvae feeding within the stem, severing the vascular system.” Dead heart” is the damage symptom of the tiller before flowering. ‘Dead
# heart’ is easily pulled out from the tiller. Symptoms of damage by larval feeding is indicated by frass in a culm, or discolouration and exit holes on the leaf sheaths and culm. When damage occurs befor
#e maximum tillering, the plant partially compensates by producing additional tillers."
    disease_id INTEGER,
    crop_id INTEGER,
    disease_name VARCHAR(255),
    disease_name_telugu VARCHAR(255),
    scientific_name VARCHAR(255),
    image_path VARCHAR(255),
    symptoms VARCHAR(255),
    PRIMARY KEY (disease_id, crop_id)
)
CREATE TABLE IF NOT EXISTS `diseasePesticideMapping` (
#==> ../../data/agronomic/diseasePesticideMapping.csv <==
#"disease_pesticide_map_id";"crop_code";"disease_id";"pesticide_code";"min_dosage_per_ha";"max_dosage_per_ha";"min_dilute_water";"max_dilute_water";"waiting_period_days";"crop_stage"
#1;6;1;5;"666";"1000";"300";"500";"15";""
    disease_pesticide_map_id INTEGER,
    crop_code INTEGER,
    disease_id INTEGER,
    pesticide_code INTEGER,
    min_dosage_per_ha VARCHAR(255),
    max_dosage_per_ha VARCHAR(255),
    min_dilute_water VARCHAR(255),
    max_dilute_water VARCHAR(255),
    waiting_period_days VARCHAR(255),
    crop_stage VARCHAR(255),
    PRIMARY KEY (disease_pesticide_map_id)
)
CREATE TABLE IF NOT EXISTS `fertilizerCropMap` (
#==> ../../data/agronomic/fertilizerCropMap.csv <==
#"fertilizer_map_id";"fertilizer_code";"crop_id";"index_value";"nutrient_id"
#1;6;6;0.0;1

    fertilizer_map_id INTEGER,
    fertilizer_code INTEGER,
    crop_id INTEGER,
    index_value REAL,
    nutrient_id INTEGER,
    PRIMARY KEY (fertilizer_map_id)
)
CREATE TABLE IF NOT EXISTS `fertilizerMaster` (
#==> ../../data/agronomic/fertilizerMaster.csv <==
#"fertilizer_id";"fertilizer_name";"fertilizer_code";"fertilizer_type";"brand_name";"company_name";"price";"quantity";"image_path"
#1;"MOP";"1";"";"Potash";"CIL";0.0;0.0;""
    fertilizer_id INTEGER,
    fertilizer_name VARCHAR(255),
    fertilizer_code VARCHAR(255),
    fertilizer_type VARCHAR(255),
    brand_name VARCHAR(255),
    company_name VARCHAR(255),
    price REAL,
    quantity REAL,
    image_path VARCHAR(255),
    PRIMARY KEY (fertilizer_id)

)
CREATE TABLE IF NOT EXISTS `pesticideMaster` (
#==> ../../data/agronomic/pesticideMaster.csv <==
#"pesticide_id";"pesticide_name";"pesticide_code";"price";"quantity";"type";"iupa_number";"mfg_local";"company_name"
#1;"24-D Ethyl Ester Technical 97% min. ";1;0.0;0.0;"Herbicide";"45";"Y";"Insecticides India Ltd Delhi "
    pesticide_id INTEGER,
    pesticide_name VARCHAR(255),
    pesticide_code INTEGER,
    price REAL,
    quantity REAL,
    type VARCHAR(255),
    iupa_number VARCHAR(255),
    mfg_local VARCHAR(255),
    company_name VARCHAR(255),
    PRIMARY KEY (pesticide_id)
)


#==> ../../data/agronomic/cropMaster.csv <==
#"crop_id";"crop_name";"crop_code";"variety_name"
#1;"GARLIC";"1";"BULB VEGETABLES"
COPY cropMaster()
FROM '/docker-entrypoint-initdb.d/cropMaster.csv'
DELIMITER ','
CSV HEADER;


#==> ../../data/agronomic/diseaseMaster.csv <==
#"disease_id";"crop_id";"disease_name";"disease_name_telugu";"scientific_name";"image_path";"symptoms"
#1;6;"Stem Borer";"";"Scirpophaga incertulas";"";"Damage is caused by larvae feeding within the stem, severing the vascular system.” Dead heart” is the damage symptom of the tiller before flowering. ‘Dead
# heart’ is easily pulled out from the tiller. Symptoms of damage by larval feeding is indicated by frass in a culm, or discolouration and exit holes on the leaf sheaths and culm. When damage occurs befor
#e maximum tillering, the plant partially compensates by producing additional tillers."
COPY diseaseMaster()
FROM '/docker-entrypoint-initdb.d/data.csv'
DELIMITER ','
CSV HEADER;


#==> ../../data/agronomic/diseasePesticideMapping.csv <==
#"disease_pesticide_map_id";"crop_code";"disease_id";"pesticide_code";"min_dosage_per_ha";"max_dosage_per_ha";"min_dilute_water";"max_dilute_water";"waiting_period_days";"crop_stage"
#1;6;1;5;"666";"1000";"300";"500";"15";""
COPY diseasePesticideMapping()
FROM '/docker-entrypoint-initdb.d/data.csv'
DELIMITER ','
CSV HEADER;


COPY fertilizerCropMap()
FROM '/docker-entrypoint-initdb.d/data.csv'
DELIMITER ','
CSV HEADER;


COPY fertilizerMaster()
FROM '/docker-entrypoint-initdb.d/data.csv'
DELIMITER ','
CSV HEADER;


COPY pesticideMaster()
FROM '/docker-entrypoint-initdb.d/data.csv'
DELIMITER ','
CSV HEADER;

