<template>
  <main>
    <div v-if="loading" class="loader"></div>
  </main>
</template>

<script>
export default {
  name: "Loader",
  props : {
      loading: Boolean
  }
};
</script>


<style  scoped>
.loader {
  border: 16px solid #f3f3f3;
  border-top: 16px solid #3498db;
  border-radius: 50%;
  width: 36px;
  height: 36px;
  animation: spin 2s linear infinite;
  margin: 45% auto;
  z-index: 1000;
}

@keyframes spin {
  0% {
    transform: rotate(0deg);
  }
  100% {
    transform: rotate(360deg);
  }
}
</style>