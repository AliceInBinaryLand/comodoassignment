<template>
  <v-footer padless app inset>
    <v-col class="text-right" cols="3">
      {{ new Date().getFullYear() }} — <strong>ICSPOT</strong>
    </v-col>
  </v-footer>
</template>

<script>
export default {
  name: "DmFooter",
};
</script>

<style scoped></style>
