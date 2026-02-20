import { Container, Title, Text, Button, Group } from '@mantine/core';
import { IconShoppingCartOff } from '@tabler/icons-react';

export function CartPage() {
  // TODO: Implement cart functionality
  const items = [];

  return (
    <Container size="md" py="xl">
      <Title order={2} mb="xl">Shopping Cart</Title>

      {items.length === 0 ? (
        <Text c="dimmed" ta="center" py="xl">
          <IconShoppingCartOff size={48} style={{ display: 'block', margin: '0 auto 16px' }} />
          Your cart is empty
        </Text>
      ) : (
        <div>
          {/* TODO: Cart items list */}
          <Button fullWidth size="lg">
            Checkout
          </Button>
        </div>
      )}
    </Container>
  );
}
