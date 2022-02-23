<!DOCTYPE html>
<html>
<head>    
    <style>
    p {
        font-size: 10px;
    }
    table {
        font-family: arial, sans-serif;
        border-collapse: collapse;
        width: 100%;}
    td, th {
        border: 1px solid #dddddd;
        text-align: left;
        width:14%;
        padding: 8px;}
    </style>
</head>   
<body>
    <%@ page import="java.util.List,java.time.LocalDate,com.revature.weddingDreams.services.*,com.revature.weddingDreams.daos.*,com.revature.weddingDreams.models.*" %>
    
    <%!  public String getNumberAssetTypesAvailable(List<Asset> list) {
        int[] counts = new int[5];
        for(int i =0; i < list.size(); i++) {
            counts[list.get(i).getAssetTypeID()-1] += 1;
        }
        
        String returnStr = "<p>"+String.valueOf(counts[0])+" venues available</p>" +
                        "<p>"+String.valueOf(counts[1])+" caterers available</p>" +
                        "<p>"+String.valueOf(counts[2])+" florists available</p>" +
                        "<p>"+String.valueOf(counts[3])+" photographers available</p>" +
                        "<p>"+String.valueOf(counts[4])+" musicians available</p>";			
        return returnStr;
    } %>

    
    <%  User authUser =  (User) session.getAttribute("authUser"); %>
<!-- If user hasn't created a wedding, or chosen a date, create calendar -->
    <%  if( authUser.getWedding() == null && request.getParameter("day") == null) { // user needs to create wedding, load create wedding form		
            int year = LocalDate.now().getYear();
            int month = LocalDate.now().getMonthValue();
            int day = LocalDate.now().getDayOfWeek().getValue();
            int lengthOfMonth = LocalDate.now().lengthOfMonth();
            
            int firstOfMonthDay = LocalDate.now().withDayOfMonth(1).getDayOfWeek().getValue();%>
            
            <table>
                <tr><th>Sun  </th><th>Mon  </th><th>Tues </th><th>Wed  </th><th>Thurs</th><th>Fri  </th><th>Sat  </th></tr>
                <tr>

    <%      EmployeeDAO employeeDAO = new EmployeeDAO(); %>
    <%      EmployeeService employeeService = new EmployeeService(employeeDAO); %>
    <%      java.util.List<com.revature.weddingDreams.models.Asset> list = employeeService.getAllAssets(); %>
            
    <%      int i = 0;
            for(i=0;i<6;i++) {
                if(i == firstOfMonthDay) { %>
                    <td>1 <% out.println(getNumberAssetTypesAvailable(list)); %><a href="betrothed-dash?day=1&month=<%out.print(month);%>&year=<%out.print(year);%>">Select this day</a></td>
    <%              while(i < 6) { %>
                        <td><% out.print(i+" "); out.println(getNumberAssetTypesAvailable(list)); %><a href="betrothed-dash?day=<%out.print(i);%>&month=<%out.print(month);%>&year=<%out.print(year);%>">Select this day</a></td>
    <%                  i++;
                    } %>
    <%          }
                else { %>
                    <td>  </td>
    <%          } %>
            
        
    <%      } %>
            </tr>
            <tr>
    <%      i-=1;
            int dayOfWeekCount = 1;
            while(i <= lengthOfMonth) { %>    
                <td><% out.print(i+" "); out.println(getNumberAssetTypesAvailable(list)); %><a href="betrothed-dash?day=<%out.print(i);%>&month=<%out.print(month);%>&year=<%out.print(year);%>">Select this day</a></td>
            
    <%          if(dayOfWeekCount == 7) {    
                    dayOfWeekCount = 0; %>
                    </tr>
    <%          } %>     
    <%          i++;
                dayOfWeekCount++;
            } %> 
             </table>
    <%  }
        else  { %>
    <%      EmployeeDAO employeeDAO = new EmployeeDAO();
            EmployeeService employeeService = new EmployeeService(employeeDAO);
            BetrothedDAO betrothedDAO = new BetrothedDAO();
            BetrothedService betrothedService = new BetrothedService(betrothedDAO);

	        List<Asset> assetList = employeeService.getAllAssets(); 
			List<AssetType> assetTypesList = employeeService.getAssetTypes();
            Wedding wedding = new Wedding();
            boolean weddingExists = false;
            double venuePrice = 0, catererPrice = 0, floristPrice = 0, photographerPrice = 0, musicianPrice = 0;
            if(authUser.getWedding() != null) { 
                weddingExists = true;
                wedding = betrothedService.getWeddingByID(authUser.getWedding());
            } %>
            
            <form action="betrothed-dash" method="post">
                <table>
                    <tr>
                        <th>Your Wedding Selections</th><th></th>
                        <th>Change selections</th><th></th>
                    </tr>
                        <%  Asset chosenAsset = new Asset(); %>
<!-- VENUE -->
                <%  String assetTypeName = assetTypesList.get(0).getAssetType(); 
                    int assetTypeID = assetTypesList.get(0).getAssetTypeID();    %>    
                    <tr> 
                        <!-- wedding data-->
                <%      if(weddingExists) {
                            chosenAsset = employeeService.getAssetByID(wedding.getVenueID());%>
                            <td><p><%out.print(chosenAsset.getCompanyName());%></p></td>
                            <% venuePrice = chosenAsset.getPrice(); %>
                            <td><p><%out.print("$"+(venuePrice));%></p></td>
                <%          }   
                            else { %>
                                <td><p> No Venue Selected</p></td>
                                <td></td>
                <%          } %>                            
                        <td> <!-- wedding selector -->
                            <label for="<% out.print(assetTypeName); %>">Select a <% out.print(assetTypeName); %></label>
                        </td>
                        <td>
                            <select name="<% out.print(assetTypeName); %>">
                <%          for(int j =0; j < assetList.size(); j++) {
                                Asset asset = assetList.get(j);
                                if(asset.getAssetTypeID() == assetTypeID) {
                                    double price = asset.getPrice(); %>
                                    <option value="<% out.print(asset.getAssetID()); %>"><% out.print(asset.getCompanyName());%>  $<%out.print(price);%></option>
                <%              } 
                            }    %>
                                
                                <option value="null">None</option>
                            </select>
                        </td>
                    </tr>

<!-- CATERER -->
                <%  assetTypeName = assetTypesList.get(1).getAssetType(); 
                    assetTypeID = assetTypesList.get(1).getAssetTypeID();    %>    
                    <tr> 
                        <!-- wedding data-->
                <%      if(weddingExists) {
                            chosenAsset = employeeService.getAssetByID(wedding.getCatererID());%>
                            <td><p><%out.print(chosenAsset.getCompanyName());%></p></td>
                            <% catererPrice = chosenAsset.getPrice(); %>
                            <td><p><%out.print("$"+(catererPrice));%></p></td>
                <%          }   
                            else { %>
                                <td><p> No Caterer Selected</p></td>
                                <td></td>
                <%          } %>                            
                        <td> <!-- wedding selector -->
                            <label for="<% out.print(assetTypeName); %>">Select a <% out.print(assetTypeName); %></label>
                        </td>
                        <td>
                            <select name="<% out.print(assetTypeName); %>">
                <%          for(int j =0; j < assetList.size(); j++) {
                                Asset asset = assetList.get(j);
                                if(asset.getAssetTypeID() == assetTypeID) {
                                    double price = asset.getPrice(); %>
                                    <option value="<% out.print(asset.getAssetID()); %>"><% out.print(asset.getCompanyName());%>  $<%out.print(price);%></option>
                <%              } 
                            }    %>
                                
                                <option value="null">None</option>
                            </select>
                        </td>
                    </tr>                    

<!-- FLORIST -->
                <%  assetTypeName = assetTypesList.get(2).getAssetType(); 
                    assetTypeID = assetTypesList.get(2).getAssetTypeID();    %>    
                    <tr> 
                        <!-- wedding data-->
                <%      if(weddingExists) {
                            chosenAsset = employeeService.getAssetByID(wedding.getFloristID());%>
                            <td><p><%out.print(chosenAsset.getCompanyName());%></p></td>
                            <% floristPrice = chosenAsset.getPrice(); %>
                            <td><p><%out.print("$"+(floristPrice));%></p></td>
                <%          }   
                            else { %>
                                <td><p> No Florist Selected</p></td>
                                <td></td>
                <%          } %>                            
                        <td> <!-- wedding selector -->
                            <label for="<% out.print(assetTypeName); %>">Select a <% out.print(assetTypeName); %></label>
                        </td>
                        <td>
                            <select name="<% out.print(assetTypeName); %>">
                <%          for(int j =0; j < assetList.size(); j++) {
                                Asset asset = assetList.get(j);
                                if(asset.getAssetTypeID() == assetTypeID) {
                                    double price = asset.getPrice(); %>
                                    <option value="<% out.print(asset.getAssetID()); %>"><% out.print(asset.getCompanyName());%>  $<%out.print(price);%></option>
                <%              } 
                            }    %>
                                
                                <option value="null">None</option>
                            </select>
                        </td>
                    </tr>  

<!-- PHOTOGRAPHER -->
                <%  assetTypeName = assetTypesList.get(3).getAssetType(); 
                    assetTypeID = assetTypesList.get(3).getAssetTypeID();    %>    
                    <tr> 
                        <!-- wedding data-->
                <%      if(weddingExists) {
                            chosenAsset = employeeService.getAssetByID(wedding.getPhotographerID());%>
                            <td><p><%out.print(chosenAsset.getCompanyName());%></p></td>
                            <% photographerPrice = chosenAsset.getPrice(); %>
                            <td><p><%out.print("$"+(photographerPrice));%></p></td>
                <%          }   
                            else { %>
                                <td><p> No Photographer Selected</p></td>
                                <td></td>
                <%          } %>                            
                        <td> <!-- wedding selector -->
                            <label for="<% out.print(assetTypeName); %>">Select a <% out.print(assetTypeName); %></label>
                        </td>
                        <td>
                            <select name="<% out.print(assetTypeName); %>">
                <%          for(int j =0; j < assetList.size(); j++) {
                                Asset asset = assetList.get(j);
                                if(asset.getAssetTypeID() == assetTypeID) {
                                    double price = asset.getPrice(); %>
                                    <option value="<% out.print(asset.getAssetID()); %>"><% out.print(asset.getCompanyName());%>  $<%out.print(price);%></option>
                <%              } 
                            }    %>
                                
                                <option value="null">None</option>
                            </select>
                        </td>
                    </tr>
                    
<!-- MUSICIAN -->
                <%  assetTypeName = assetTypesList.get(4).getAssetType(); 
                    assetTypeID = assetTypesList.get(4).getAssetTypeID();    %>    
                    <tr> 
                        <!-- wedding data-->
                <%      if(weddingExists) {
                            chosenAsset = employeeService.getAssetByID(wedding.getMusicianID());%>
                            <td><p><%out.print(chosenAsset.getCompanyName());%></p></td>
                            <% musicianPrice = chosenAsset.getPrice(); %>
                            <td><p><%out.print("$"+(musicianPrice));%></p></td>
                <%          }   
                            else { %>
                                <td><p> No Musician Selected</p></td>
                                <td></td>
                <%          } %>                            
                        <td> <!-- wedding selector -->
                            <label for="<% out.print(assetTypeName); %>">Select a <% out.print(assetTypeName); %></label>
                        </td>
                        <td>
                            <select name="<% out.print(assetTypeName); %>">
                <%          for(int j =0; j < assetList.size(); j++) {
                                Asset asset = assetList.get(j);
                                if(asset.getAssetTypeID() == assetTypeID) {
                                    double price = asset.getPrice(); %>
                                    <option value="<% out.print(asset.getAssetID()); %>"><% out.print(asset.getCompanyName());%>  $<%out.print(price);%></option>
                <%              } 
                            }    %>
                                
                                <option value="null">None</option>
                            </select>
                        </td>
                    </tr>

<!-- BUDGET and COST -->
                    <tr>
                        <% if(weddingExists) { %>
                        <td>
                            <p> Your Budget</p>
                            <p><%out.print(wedding.getWeddingBudget());%></p>
                        </td>
                        <td>
                            <!-- summed Cost-->
                            <p>Wedding Cost</p>
                            <p>$<%out.print( venuePrice + catererPrice + floristPrice + photographerPrice + musicianPrice );%> </p>
                        </td>
                        <% } 
                           else { %>
                                <td></td>       
                                <td></td>
                        <%    } %>

                        <td></td>
                        <td>
                            <label for="wedding-budget">Budget</label>
                            <input type="text" name="wedding-budget" required>                
                            <br />                
                        </td>
                    </tr>
                </table>
<!-- Hidden WEDDING DATE and SUBMIT -->
            <input type="hidden" name="wedding-date" value=" <% out.print(request.getParameter("year")+"-"+request.getParameter("month")+"-"+request.getParameter("day")); %> ">	  
            <% if(weddingExists) { %>
                <input type="submit" value="Update Wedding">
            <% }
            else { %>
                <input type="submit" value="Create Wedding">
            <% } %>
            </form>

    <%  } %>
     
</body>
</html>

