<template>
  <v-img
      max-height="150"
      max-width="250"
      src="../../../assets/todos-logo.png"
  ></v-img>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters({
      getMini: "layout/getMini",
    }),
    getTheme() {
      // if (this.$vuetify.theme.dark) {
      //   return "grey";
      // } else
        return "original";
    },
    logoSrc() {
      if (this.getMini) {
        return "short";
      } else return "long";
    },
  },
};
</script>

<style scoped></style>
