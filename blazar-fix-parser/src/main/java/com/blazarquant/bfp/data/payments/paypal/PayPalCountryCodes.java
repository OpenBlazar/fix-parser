package com.blazarquant.bfp.data.payments.paypal;

/**
 * @author Wojciech Zankowski
 */
public enum PayPalCountryCodes {

    ALBANIA("AL"),
    ALGERIA("DZ"),
    ANDORRA("AD"),
    ANGOLA("AO"),
    ANGUILLA("AI"),
    ANTIGUA_AND_BARBUDA("AG"),
    ARGENTINA("AR"),
    ARMENIA("AM"),
    ARUBA("AW"),
    AUSTRALIA("AU"),
    AUSTRIA("AT"),
    AZERBAIJAN("AZ"),
    BAHAMAS("BS"),
    BAHRAIN("BH"),
    BARBADOS("BB"),
    BELARUS("BY"),
    BELGIUM("BE"),
    BELIZE("BZ"),
    BENIN("BJ"),
    BERMUDA("BM"),
    BHUTAN("BT"),
    BOLIVIA("BO"),
    BOSNIA_AND_HERZEGOVINA("BA"),
    BOTSWANA("BW"),
    BRAZIL("BR"),
    BRITISH_VIRGIN_ISLANDS("VG"),
    BRUNEI("BN"),
    BULGARIA("BG"),
    BURKINA_FASO("BF"),
    BURUNDI("BI"),
    CAMBODIA("KH"),
    CAMEROON("CM"),
    CANADA("CA"),
    CAPE_VERDE("CV"),
    CAYMAN_ISLANDS("KY"),
    CHAD("TD"),
    CHILE("CL"),
    CHINA("CN"),
    CHINA_WORLDWIDE("C2"),
    COLOMBIA("CO"),
    COMOROS("KM"),
    CONGO_BRAZZAVILLE("CG"),
    CONGO_KINSHASA("CD"),
    COOK_ISLANDS("CK"),
    COSTA_RICA("CR"),
    CÔTE_DIVOIRE("CI"),
    CROATIA("HR"),
    CYPRUS("CY"),
    CZECH_REPUBLIC("CZ"),
    DENMARK("DK"),
    DJIBOUTI("DJ"),
    DOMINICA("DM"),
    DOMINICAN_REPUBLIC("DO"),
    ECUADOR("EC"),
    EGYPT("EG"),
    EL_SALVADOR("SV"),
    ERITREA("ER"),
    ESTONIA("EE"),
    ETHIOPIA("ET"),
    FALKLAND_ISLANDS("FK"),
    FAROE_ISLANDS("FO"),
    FIJI("FJ"),
    FINLAND("FI"),
    FRANCE("FR"),
    FRENCH_GUIANA("GF"),
    FRENCH_POLYNESIA("PF"),
    GABON("GA"),
    GAMBIA("GM"),
    GEORGIA("GE"),
    GERMANY("DE"),
    GIBRALTAR("GI"),
    GREECE("GR"),
    GREENLAND("GL"),
    GRENADA("GD"),
    GUADELOUPE("GP"),
    GUATEMALA("GT"),
    GUINEA("GN"),
    GUINEA_BISSAU("GW"),
    GUYANA("GY"),
    HONDURAS("HN"),
    HONG_KONG_SAR_CHINA("HK"),
    HUNGARY("HU"),
    ICELAND("IS"),
    INDIA("IN"),
    INDONESIA("ID"),
    IRELAND("IE"),
    ISRAEL("IL"),
    ITALY("IT"),
    JAMAICA("JM"),
    JAPAN("JP"),
    JORDAN("JO"),
    KAZAKHSTAN("KZ"),
    KENYA("KE"),
    KIRIBATI("KI"),
    KUWAIT("KW"),
    KYRGYZSTAN("KG"),
    LAOS("LA"),
    LATVIA("LV"),
    LESOTHO("LS"),
    LIECHTENSTEIN("LI"),
    LITHUANIA("LT"),
    LUXEMBOURG("LU"),
    MACEDONIA("MK"),
    MADAGASCAR("MG"),
    MALAWI("MW"),
    MALAYSIA("MY"),
    MALDIVES("MV"),
    MALI("ML"),
    MALTA("MT"),
    MARSHALL_ISLANDS("MH"),
    MARTINIQUE("MQ"),
    MAURITANIA("MR"),
    MAURITIUS("MU"),
    MAYOTTE("YT"),
    MEXICO("MX"),
    MICRONESIA("FM"),
    MOLDOVA("MD"),
    MONACO("MC"),
    MONGOLIA("MN"),
    MONTENEGRO("ME"),
    MONTSERRAT("MS"),
    MOROCCO("MA"),
    MOZAMBIQUE("MZ"),
    NAMIBIA("NA"),
    NAURU("NR"),
    NEPAL("NP"),
    NETHERLANDS("NL"),
    NEW_CALEDONIA("NC"),
    NEW_ZEALAND("NZ"),
    NICARAGUA("NI"),
    NIGER("NE"),
    NIGERIA("NG"),
    NIUE("NU"),
    NORFOLK_ISLAND("NF"),
    NORWAY("NO"),
    OMAN("OM"),
    PALAU("PW"),
    PANAMA("PA"),
    PAPUA_NEW_GUINEA("PG"),
    PARAGUAY("PY"),
    PERU("PE"),
    PHILIPPINES("PH"),
    PITCAIRN_ISLANDS("PN"),
    POLAND("PL"),
    PORTUGAL("PT"),
    QATAR("QA"),
    RÉUNION("RE"),
    ROMANIA("RO"),
    RUSSIA("RU"),
    RWANDA("RW"),
    SAMOA("WS"),
    SAN_MARINO("SM"),
    SÃO_TOMÉ_AND_PRÍNCIPE("ST"),
    SAUDI_ARABIA("SA"),
    SENEGAL("SN"),
    SERBIA("RS"),
    SEYCHELLES("SC"),
    SIERRA_LEONE("SL"),
    SINGAPORE("SG"),
    SLOVAKIA("SK"),
    SLOVENIA("SI"),
    SOLOMON_ISLANDS("SB"),
    SOMALIA("SO"),
    SOUTH_AFRICA("ZA"),
    SOUTH_KOREA("KR"),
    SPAIN("ES"),
    SRI_LANKA("LK"),
    ST_HELENA("SH"),
    ST_KITTS_AND_NEVIS("KN"),
    ST_LUCIA("LC"),
    ST_PIERRE_AND_MIQUELON("PM"),
    ST_VINCENT_AND_GRENADINES("VC"),
    SURINAME("SR"),
    SVALBARD_AND_JAN_MAYEN("SJ"),
    SWAZILAND("SZ"),
    SWEDEN("SE"),
    SWITZERLAND("CH"),
    TAIWAN("TW"),
    TAJIKISTAN("TJ"),
    TANZANIA("TZ"),
    THAILAND("TH"),
    TOGO("TG"),
    TONGA("TO"),
    TRINIDAD_AND_TOBAGO("TT"),
    TUNISIA("TN"),
    TURKEY("TR"),
    TURKMENISTAN("TM"),
    TURKS_AND_CAICOS_ISLANDS("TC"),
    TUVALU("TV"),
    UGANDA("UG"),
    UKRAINE("UA"),
    UNITED_ARAB_EMIRATES("AE"),
    UNITED_KINGDOM("GB"),
    UNITED_STATES("US"),
    URUGUAY("UY"),
    VANUATU("VU"),
    VATICAN_CITY("VA"),
    VENEZUELA("VE"),
    VIETNAM("VN"),
    WALLIS_AND_FUTUNA("WF"),
    YEMEN("YE"),
    ZAMBIA("ZM"),
    ZIMBABWE("ZW");

    private final String countryCode;

    PayPalCountryCodes(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

}
