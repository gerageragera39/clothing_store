import { useState } from 'react';
import { Container, Title, Grid, Card, Image, Text, Button, Group, Select, TextInput, Loader, Box } from '@mantine/core';
import { IconShoppingBag, IconSearch } from '@tabler/icons-react';
import { useQuery } from '@tanstack/react-query';
import { clothesService } from '../services';
import type { Clothes } from '../types';

export function CatalogPage() {
  const [sex, setSex] = useState<string | null>(null);
  const [type, setType] = useState<string | null>(null);
  const [searchQuery, setSearchQuery] = useState('');

  const { data: clothes, isLoading } = useQuery({
    queryKey: ['clothes', { sex, type, searchQuery }],
    queryFn: () => {
      // Use search API if there's a query, otherwise use regular clothes API
      if (searchQuery.trim()) {
        return fetch(`http://localhost:8080/api/search?query=${encodeURIComponent(searchQuery)}${sex ? `&sex=${sex}` : ''}${type ? `&type=${type}` : ''}`)
          .then(res => res.json());
      }
      return clothesService.getAll({ sex: sex || undefined, type: type || undefined });
    },
  });

  return (
    <Container size="xl" py="xl">
      <Title order={2} mb="xl">Catalog</Title>

      <Box mb="xl">
        <TextInput
          placeholder="Search products..."
          leftSection={<IconSearch size={18} />}
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.currentTarget.value)}
          mb="md"
          size="lg"
        />
        
        <Group gap="md">
          <Select
            label="Sex"
            placeholder="All"
            data={['Male', 'Female', 'Unisex']}
            value={sex}
            onChange={setSex}
            clearable
            style={{ width: 200 }}
          />
          
          <Select
            label="Type"
            placeholder="All"
            data={['TSHIRT', 'HOODIE', 'JEANS', 'SHORTS', 'DRESS', 'JACKET']}
            value={type}
            onChange={setType}
            clearable
            style={{ width: 200 }}
          />
        </Group>
      </Box>

      {isLoading ? (
        <Loader />
      ) : clothes && clothes.length > 0 ? (
        <Grid gutter="md">
          {clothes.map((item: Clothes) => (
            <Grid.Col key={item.id} span={{ base: 12, sm: 6, md: 4, lg: 3 }}>
              <Card shadow="sm" padding="lg" radius="md" withBorder>
                <Card.Section>
                  <Image
                    src={item.images?.[0]?.url || 'https://via.placeholder.com/300'}
                    alt={item.title}
                    h={200}
                    fit="cover"
                  />
                </Card.Section>

                <Group justify="space-between" mt="md" mb="xs">
                  <Text fw={500}>{item.title}</Text>
                </Group>

                <Text size="sm" c="dimmed" lineClamp={2}>
                  {item.description}
                </Text>

                <Group justify="space-between" mt="md">
                  <Text fw={700} size="lg">
                    ${typeof item.price === 'number' ? item.price.toFixed(2) : '0.00'}
                  </Text>
                  <Button
                    size="compact-sm"
                    leftSection={<IconShoppingBag size={16} />}
                  >
                    Add to Cart
                  </Button>
                </Group>

                <Group gap="xs" mt="sm">
                  {item.colors?.slice(0, 3).map((color) => (
                    <Text key={color} size="xs" c="dimmed">
                      {color}
                    </Text>
                  ))}
                </Group>
              </Card>
            </Grid.Col>
          ))}
        </Grid>
      ) : (
        <Text c="dimmed">No products found</Text>
      )}
    </Container>
  );
}
