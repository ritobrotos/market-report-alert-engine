package main.java.constant;

import com.google.common.collect.Lists;

import model.StockFact;

import java.util.List;

public class FnoStockList {

  public static List<StockFact> getFnoStockList() {

    List<StockFact> fnoStockList = Lists.newArrayList(
        new StockFact("ACC Ltd", "ACC.NS"),
//        new StockFact("Adani Enterprises Ltd", "ADANIENT.NS"),
//        new StockFact("Adani Ports and Special Economic Zone Limited", "ADANIPORTS.NS"),
//        new StockFact("Adani Power Limited", "ADANIPOWER.BO"),
        new StockFact("Ajanta Pharma Limited", "AJANTPHARM.NS"),
        new StockFact("Allahabad Bank", "ALBK.NS"),
        new StockFact("Amara Raja Batteries Limited", "AMARAJABAT.NS"),
        new StockFact("Ambuja Cements Limited", "AMBUJACEM.NS"),
        new StockFact("Andhra Bank", "ANDHRABANK.NS"),
        new StockFact("Apollo Hospitals Enterprise Limited", "APOLLOHOSP.NS"),
        new StockFact("Apollo Tyres Limited", "APOLLOTYRE.NS"),
        new StockFact("Arvind Ltd", "ARVIND.NS"),
        new StockFact("Ashok Leyland Limited", "ASHOKLEY.NS"),
        new StockFact("Asian Paints Ltd", "ASIANPAINT.BO"),
        new StockFact("Aurobindo Pharma Limited", "AUROPHARMA.NS"),
        new StockFact("Axis Bank Limited", "AXISBANK.NS"),

        new StockFact("Bajaj Auto Limited", "BAJAJ-AUTO-EQ.NS"),
        new StockFact("Bajaj Finserv Limited", "BAJAJFINSV.NS"),
        new StockFact("Bajaj Finance Limited", "BAJFINANCE.NS"),
        new StockFact("Balkrishna Industries Limited", "BALKRISIND.NS"),
        new StockFact("Bank of Baroda", "BANKBARODA.NS"),
        new StockFact("Bank of India Limited", "BANKINDIA.NS"),
        new StockFact("Bata India Limited", "BATAINDIA.NS"),
        new StockFact("Bharat Electronics Limited", "BEL.NS"),
        new StockFact("BEML Ltd", "BEML.NS"),
        new StockFact("Berger Paints India Limited", "BERGEPAINT.NS"),
        new StockFact("Bharat Financial Inclusion Limited", "BHARATFIN.NS"),
        new StockFact("Bharat Forge Limited", "BHARATFORG.NS"),
        new StockFact("Bharti Airtel Limited", "BHARTIARTL.NS"),
        new StockFact("Bharat Heavy Electricals Limited", "BHEL.NS"),
        new StockFact("Biocon Limited", "BIOCON.NS"),
        new StockFact("Bosch Limited", "BOSCHLTD.NS"),
        new StockFact("Bharat Petroleum Corporation Limited", "BPCL.NS"),
        new StockFact("Britannia Industries Limited", "BRITANNIA.NS"),

        new StockFact("Cadila Healthcare Limited", "CADILAHC.NS"),
        new StockFact("Can Fin Homes Limited", "CANFINHOME.NS"),
        new StockFact("Canara Bank", "CANBK.NS"),
        new StockFact("Castrol India Limited", "CASTROLIND.NS"),
        new StockFact("CEAT Limited", "CEATLTD.NS"),
        new StockFact("Century Textiles and Industries Limited", "CENTURYTEX.NS"),
        new StockFact("CESC Limited", "CESC.NS"),
        new StockFact("CG Power and Industrial Solutions Limited", "CGPOWER.NS"),
        new StockFact("Chennai Petroleum Corporation Limited", "CHENNPETRO.NS"),
        new StockFact("Cholamandalam Investment and Finance Company Limited ", "CHOLAFIN.NS"),
        new StockFact("Cipla Limited", "CIPLA.NS"),
        new StockFact("Coal India Limited", "COALINDIA.NS"),
        new StockFact("Colgate-Palmolive (India) Ltd", "COLPAL.NS"),
        new StockFact("Cummins India Limited", "CUMMINSIND.NS"),
        new StockFact("Container Corporation of India Limited", "CONCOR.NS"),

        new StockFact("Dabur India Ltd", "DABUR.NS"),
        new StockFact("DCB Bank Limited", "DCBBANK.NS"),
        new StockFact("Dewan Housing Finance Corporation Limited", "DHFL.NS"),
        new StockFact("Dish TV India Limited", "DISHTV.NS"),
        new StockFact("Divi's Laboratories Limited","DIVISLAB.NS"),
        new StockFact("DLF Limited", "DLF.NS"),
        new StockFact("Dr. Reddy's Laboratories Limited", "DRREDDY.NS"),

        new StockFact("Eicher Motors Ltd", "EICHERMOT.NS"),
        new StockFact("Engineers India Limited", "ENGINERSIN.NS"),
        new StockFact("Equitas Holdings Limited", "EQUITAS.NS"),
        new StockFact("Escorts Limited", "ESCORTS.NS"),
        new StockFact("Exide Industries Limited", "EXIDEIND.NS"),

        new StockFact("The Federal Bank Limited", "FEDERALBNK.NS"),

        new StockFact("GAIL (India) Limited", "GAIL.NS"),
        new StockFact("Glenmark Pharmaceuticals Limited", "GLENMARK.NS"),
        new StockFact("GMR Infrastructure Limited", "GMRINFRA.NS"),
        new StockFact("Godfrey Phillips India Limited", "GODFRYPHLP.NS"),
        new StockFact("Godrej Consumer Products Limited", "GODREJCP.NS"),
        new StockFact("Godrej Industries Limited", "GODREJIND.NS"),
        new StockFact("Grasim Industries Limited", "GRASIM.NS"),
        new StockFact("Gujarat State Fertilizers & Chemicals Limited", "GSFC.NS"),

        new StockFact("Havells India Limited", "HAVELLS.NS"),
        new StockFact("HCL Technologies Ltd", "HCLTECH.NS"),
        new StockFact("HDFC Bank Limited", "HDFCBANK.NS"),
        new StockFact("Housing Development Finance Corporation Limited", "HDFC.NS"),
        new StockFact("Hero MotoCorp Limited", "HEROMOTOCO.NS"),
        new StockFact("Hexaware Technologies Limited", "HEXAWARE.NS"),
        new StockFact("Hindalco Industries Limited", "HINDALCO.NS"),
        new StockFact("Hindustan Petroleum Corporation Limited", "HINDPETRO.NS"),
        new StockFact("Hindustan Unilever Limited", "HINDUNILVR.NS"),
        new StockFact("Hindustan Zinc Limited", "HINDZINC.NS"),

        new StockFact("ICICI Bank Limited", "ICICIBANK.NS"),
        new StockFact("ICICI Prudential Life Insurance Company Limited", "ICICIPRULI.NS"),
        new StockFact("IDBI Bank Ltd", "IDBI.NS"),
        new StockFact("Idea Cellular Limited", "IDEA.NS"),
        new StockFact("IDFC Limited", "IDFC.NS"),
        new StockFact("IDFC Bank Limited", "IDFCBANK.NS"),
        new StockFact("IFCI Limited", "IFCI.NS"),
        new StockFact("Indiabulls Housing Finance Limited", "IBULHSGFIN.NS"),
        new StockFact("Indian Bank", "INDIANB.NS"),
        new StockFact("Indian Oil Corporation Limited", "IOC.NS"),
        new StockFact("Indraprastha Gas Ltd", "IGL.NS"),
        new StockFact("IndusInd Bank Limited", "INDUSINDBK.NS"),
//        new StockFact("Infibeam Incorporation Limited", "INFIBEAM.NS"),
        new StockFact("Infosys Limited", "INFY.NS"),
        new StockFact("InterGlobe Aviation Limited", "INDIGO.NS"),
        new StockFact("IRB Infrastructure Developers Limited", "IRB.BO"),
        new StockFact("ITC Limited", "ITC.NS"),

        new StockFact("Jain Irrigation Systems Limited", "JISLJALEQS.NS"),
        new StockFact("Jaiprakash Associates Limited", "JPASSOCIAT.NS"),
        new StockFact("Jet Airways (India) Limited", "JETAIRWAYS.NS"),
        new StockFact("Jindal Steel & Power Limited", "JINDALSTEL.NS"),
        new StockFact("JSW Steel Limited", "JSWSTEEL.NS"),
        new StockFact("Jubilant FoodWorks Limited", "JUBLFOOD.NS"),
        new StockFact("Just Dial Limited", "JUSTDIAL.NS"),

        new StockFact("Kajaria Ceramics Limited", "KAJARIACER.NS"),
        new StockFact("Karnataka Bank Ltd", "KTKBANK.NS"),
        new StockFact("Kaveri Seed Company Limited", "KSCL.NS"),
        new StockFact("Kotak Mahindra Bank Limited", "KOTAKBANK.NS"),

        new StockFact("L&T Finance Holdings Limited", "L&TFH.NS"),
        new StockFact("LARSEN & TOUBRO LTD", "LT.NS"),
        new StockFact("LIC Housing Finance Limited", "LICHSGFIN.NS"),
        new StockFact("Lupin Limited", "LUPIN.NS"),

        new StockFact("Mahindra & Mahindra Financial Services Limited", "M&MFIN.NS"),
        new StockFact("Mahanagar Gas Limited", "MGL.NS"),
        new StockFact("Mahindra & Mahindra Limited", "M&M.NS"),
        new StockFact("Manappuram Finance Limited", "MANAPPURAM.NS"),
        new StockFact("Mangalore Refinery and Petrochemicals Limited", "MRPL.NS"),
        new StockFact("Marico Limited", "MARICO.NS"),
        new StockFact("Maruti Suzuki India Limited", "MARUTI.NS"),
        new StockFact("Max Financial Services Limited", "MFSL.NS"),
        new StockFact("Mindtree Limited", "MINDTREE.NS"),
        new StockFact("Motherson Sumi Systems Limited", "MOTHERSUMI.NS"),
        new StockFact("Muthoot Finance Limited", "MUTHOOTFIN.NS"),

        new StockFact("National Aluminium Company Limited", "NATIONALUM.NS"),
        new StockFact("NBCC (India) Limited", "NBCC.NS"),
        new StockFact("NCC Limited", "NCC.NS"),
        new StockFact("NHPC Limited", "NHPC.NS"),
        new StockFact("NIIT Technologies Ltd", "NIITTECH.NS"),
        new StockFact("NMDC Ltd", "NMDC.NS"),
        new StockFact("NTPC Limited", "NTPC.NS"),

        new StockFact("Oil & Natural Gas Corp Ltd", "ONGC.NS"),
        new StockFact("Oil India Limited", "OIL.NS"),
        new StockFact("Oracle Financial Services Software Limited", "OFSS.NS"),
        new StockFact("Oriental Bank of Commerce", "ORIENTBANK.NS"),

        new StockFact("Page Industries Ltd", "PAGEIND.NS"),
        new StockFact("Petronet LNG Limited", "PETRONET.NS"),
        new StockFact("Pidilite Industries Limited", "PIDILITIND.NS"),
        new StockFact("Piramal Enterprises Limited", "PEL.NS"),
        new StockFact("Power Finance Corporation Limited", "PFC.NS"),
        new StockFact("Power Grid Corporation of India Limited", "POWERGRID.NS"),
        new StockFact("Punjab National Bank", "PNB.NS"),
        new StockFact("PVR Limited", "PVR.NS"),

        new StockFact("Raymond Limited", "RAYMOND.NS"),
        new StockFact("RBL Bank Limited", "RBLBANK.NS"),
        new StockFact("Reliance Industries Limited", "RELIANCE.NS"),
        new StockFact("Repco Home Finance Limited", "REPCOHOME.NS"),
        new StockFact("Rural Electrification Corp Ltd", "RECLTD.NS"),

        new StockFact("Shree Cement Limited", "SHREECEM.NS"),
        new StockFact("Shriram Transport Finance Company Limited", "SRTRANSFIN.NS"),
        new StockFact("Siemens Limited", "SIEMENS.NS"),
        new StockFact("SRF Limited", "SRF.NS"),
        new StockFact("State Bank of India", "SBIN.NS"),
        new StockFact("Steel Authority of India Limited", "SAIL.NS"),
        new StockFact("Strides Shasun Limited", "STAR.NS"),
        new StockFact("Sun Pharmaceutical Industries Limited", "SUNPHARMA.NS"),
        new StockFact("Sun TV Network Limited", "SUNTV.NS"),
        new StockFact("Syndicate Bank", "SYNDIBANK.NS"),

        new StockFact("Tata Chemicals Limited", "TATACHEM.NS"),
        new StockFact("Tata Communications Limited", "TATACOMM.NS"),
        new StockFact("Tata Consultancy Services Limited", "TCS.NS"),
        new StockFact("Tata Elxsi Limited", "TATAELXSI.NS"),
        new StockFact("Tata Global Beverages Limited", "TATAGLOBAL.NS"),
        new StockFact("TATA MOTORS LTD - DVR", "TATAMTRDVR.BO"),
        new StockFact("Tata Motors Limited", "TATAMOTORS.NS"),
        new StockFact("The Tata Power Company Limited", "TATAPOWER.NS"),
        new StockFact("Tata Steel Limited", "TATASTEEL.NS"),
        new StockFact("Tech Mahindra Limited", "TECHM.NS"),
        new StockFact("The India Cements Limited", "INDIACEM.NS"),
        new StockFact("The Ramco Cements Limited", "RAMCOCEM.NS"),
        new StockFact("The South Indian Bank Limited", "SOUTHBANK.NS"),
        new StockFact("Titan Company Limited", "TITAN.NS"),
        new StockFact("Torrent Pharmaceuticals Limited", "TORNTPHARM.NS"),
        new StockFact("Torrent Power Limited", "TORNTPOWER.NS"),
        new StockFact("TV18 Broadcast Limited", "TV18BRDCST.NS"),
        new StockFact("TVS Motor Company Limited", "TVSMOTOR.NS"),

        new StockFact("Ujjivan Financial Services Limited", "UJJIVAN.NS"),
        new StockFact("UltraTech Cement Limited", "ULTRACEMCO.NS"),
        new StockFact("Union Bank of India", "UNIONBANK.NS"),
        new StockFact("United Breweries Limited", "UBL.NS"),
        new StockFact("United Spirits Ltd", "UNITDSPR.BO"),
        new StockFact("UPL Limited", "UPL.NS"),

        new StockFact("Vedanta Limited", "VEDL.NS"),
        new StockFact("V-Guard Industries Limited", "VGUARD.NS"),
        new StockFact("Voltas Limited", "VOLTAS.NS"),

        new StockFact("Wipro Limited", "WIPRO.NS"),
        new StockFact("Wockhardt Limited", "WOCKPHARMA.NS"),

        new StockFact("Yes Bank Limited", "YESBANK.NS"),
        new StockFact("Zee Entertainment Enterprises Limited", "ZEEL.NS")
    );

    return fnoStockList;
  }
}