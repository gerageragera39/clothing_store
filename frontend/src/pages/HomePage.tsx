import { Container, Title, Text, Button, Group } from '@mantine/core';
import { IconShoppingCart, IconArrowRight } from '@tabler/icons-react';
import { useNavigate } from 'react-router-dom';

export function HomePage() {
  const navigate = useNavigate();

  return (
    <Container size="md" style={{ textAlign: 'center', paddingTop: '80px' }}>
      <Title order={1} size="h1" mb="md">
        Welcome to Clothing Store
      </Title>
      
      <Text size="xl" c="dimmed" mb="xl">
        Discover the latest fashion trends and find your perfect style
      </Text>

      <Group justify="center" gap="md">
        <Button
          size="lg"
          rightSection={<IconShoppingCart size={20} />}
          onClick={() => navigate('/catalog')}
        >
          Shop Now
        </Button>
        
        <Button
          size="lg"
          variant="outline"
          rightSection={<IconArrowRight size={20} />}
          onClick={() => navigate('/login')}
        >
          Sign In
        </Button>
      </Group>

      <Container size="sm" style={{ marginTop: '60px' }}>
        <Group justify="center" gap="xl">
          <div>
            <Title order={3}>100+</Title>
            <Text size="sm" c="dimmed">Products</Text>
          </div>
          <div>
            <Title order={3}>20+</Title>
            <Text size="sm" c="dimmed">Brands</Text>
          </div>
          <div>
            <Title order={3}>Free</Title>
            <Text size="sm" c="dimmed">Shipping</Text>
          </div>
        </Group>
      </Container>
    </Container>
  );
}
