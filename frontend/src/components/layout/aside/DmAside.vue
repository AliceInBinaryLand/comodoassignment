<template>
  <v-navigation-drawer app permanent :mini-variant="mini" width="200">
    <template #prepend>
      <dm-brand></dm-brand>
    </template>

    <v-divider></v-divider>
    <dm-menu></dm-menu>
  </v-navigation-drawer>
</template>

<script>
import DmBrand from "@/components/layout/aside/DmBrand";
import DmMenu from "@/components/layout/aside/DmMenu";
export default {
  name: "DmAside",
  components: { DmMenu, DmBrand },
  computed: {
    mini: {
      get() {
        return this.$store.state.layout.mini;
      },
      set() {
        this.$store.commit("layout/SET_MINI");
      },
    },
  },
};
</script>

<style scoped></style>
