<template>
  <v-app>
    <v-main>
      <dm-toast></dm-toast>
      <router-view></router-view>
    </v-main>
  </v-app>
</template>

<script>
import DmToast from "@/components/common/DmToast";
export default {
  name: "layout-full",
  components: { DmToast },
  data: () => ({}),
};
</script>
<style lang="scss"></style>
