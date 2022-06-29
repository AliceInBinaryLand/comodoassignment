<template>
  <router-view></router-view>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "App",

  data: () => ({}),
  computed: {
    ...mapGetters({
      isAuthenticated: "auth/isAuthenticated",
    }),
  },
  created() {},
};
</script>
<style lang="scss"></style>
