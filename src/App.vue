<template>
  <div id="main-app" class="container">
    <div style="height: 50px; background-color: rgba(137, 196, 244);, 1;">
      <ul class="nav justify-content-end">
        <li class="nav-item">
          <router-link class="nav-link" to="/">My Reports</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/create">Create Report</router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Sign Out</a>
        </li>
      </ul>
    </div>
    <router-view/>
  </div>
</template>