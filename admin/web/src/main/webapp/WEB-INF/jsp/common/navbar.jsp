<!-- <div class="navbar">
  <div class="navbar-inner">

 <div 
    <ul class="nav pull-left">
      <li class="active"><a href="#">Home</a></li>
      <li><a href="#">NDVI</a></li>
      <li><a href="#">Link</a></li>
    </ul>
    	<a class="brand"><${username}</a>
    

  </div>
</div>
-->
<div class="navbar">
   <div class="navbar-inner">
     <div class="container">
       <div class="nav-collapse collapse navbar-responsive-collapse">
         <ul class="nav">
         
           <li class="${context=='home'?'active':'' }"><a href="<c:url value="/home"/>">Home</a></li>
           <!-- 
           <li><a href="#">Publish data</a></li>
           	
           <li class="dropdown">
             <a href="#" class="dropdown-toggle" data-toggle="dropdown">NDVI <b class="caret"></b></a>
             <ul class="dropdown-menu">
               <li><a href="#">Statistics</a></li>
               <li><a href="#">Publish</a></li>
  
             </ul>
           </li>
            -->
           <li class="${context=='users'?'active':'' }"><a href="<c:url value="/users/"/>">Users</a></li>
         </ul>
   
         <ul class="nav pull-right">

           <li class="dropdown">
             <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-user"></i> ${username} <b class="caret"></b></a>
             <ul class="dropdown-menu">
               <li><a href="<c:url value="/j_spring_security_logout" />" ><i class=" icon-off"></i> Logout</a></li>
             </ul>
           </li>
         </ul>
       </div><!-- /.nav-collapse -->
     </div>
   </div><!-- /navbar-inner -->
 </div>

