@Test
void shouldUpdateEstimationAndNotifyObservers() {
    Order order = new Order();
    order.setId(1L);
    order.setStatus(OrderStatus.MENUNGGU_ESTIMASI);

    when(orderRepo.findById(1L)).thenReturn(Optional.of(order));

    orderService.updateEstimation(1L, 3, 150_000L);

    assertEquals(3, order.getEstimatedHours());
    assertEquals(150_000L, order.getEstimatedPrice());
    assertEquals(OrderStatus.MENUNGGU_KONFIRMASI_PENGGUNA, order.getStatus());

    verify(orderRepo).save(order);
}
